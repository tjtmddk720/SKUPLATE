package com.skuplate.server.auth.filter;

import com.skuplate.server.auth.jwt.JwtTokenizer;
import com.skuplate.server.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter { // request 당 한 번만 실행되도록 -> 인증은 성공/실패인지 한 번만 판단하면 됨
    private final JwtTokenizer jwtTokenizer; // JWT를 검증하고 Claims(토큰에 포함된 정보)를 얻는 데 사용
    private final CustomAuthorityUtils authorityUtils; // JWT 검증에 성공하면 Authentication 객체에 채울 사용자의 권한을 생성하는 데 사용

    // 클라이언트 측에서 JWT를 이용해 자격 증명이 필요한 리소스에 대한 request 전송 시, request header를 통해 전달받은 JWT를 서버 측에서 검증하는 기능
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 예외가 발생 시 SecurityContext에 클라이언트의 인증 정보(Authentication 객체)가 저장
        try {
            Map<String, Object> claims = verifyJws(request); // jwt 검증 시 사용되는 메서드(아래 메서드 있음)
            setAuthenticationToContext(claims); // Authentication 객체를 SecurityContext에 저장하기 위한 메서드(아래 있음)
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
//            throw new ServletException("Expired Token");
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response); // 서명 검증에 성공, Security Context에 Authentication을 저장되면 다음 필터 호출
    }

    // OncePerRequestFilter의 shouldNotFilter()를 오버라이드 한 것, 조건에 부합 시 해당 filter 동작 수행 x, 다음 filter로 건너뛰게 해줌
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");  // Authorization header의 값을 얻음

        return authorization == null || !authorization.startsWith("Bearer");  //  Authorization header의 값이 null이거나 Authorization header의 값이 “Bearer”로 시작하지 않는다면 해당 Filter의 동작을 수행하지 않도록 정의
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); //  서명된 JWT == JWS(JSON Web Token Signed)
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key를 얻음
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // JWT에서 Claims를 파싱(=서명 검증에 성공)

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");   // JWT에서 파싱 한 Claims에서 얻음
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));  // JWT의 Claims에서 얻은 권한 정보를 기반
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);  // username과 List<GrantedAuthority를 포함한 Authentication 객체를 생성
        SecurityContextHolder.getContext().setAuthentication(authentication); //  SecurityContext에 Authentication 객체를 저장
    }
}
