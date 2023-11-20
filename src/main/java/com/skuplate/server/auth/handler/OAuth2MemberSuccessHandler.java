package com.skuplate.server.auth.handler;

import com.skuplate.server.auth.entity.RefreshToken;
import com.skuplate.server.auth.generator.OrderIdGenerator;
import com.skuplate.server.auth.jwt.JwtTokenizer;
import com.skuplate.server.auth.repository.AuthRepository;
import com.skuplate.server.auth.utils.CustomAuthorityUtils;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static com.skuplate.server.member.entity.Member.accountProvider.GOOGLE;

@AllArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;
    private final AuthRepository refreshTokenRepository;
    private final OrderIdGenerator orderIdGenerator;

    /** OAuth2 로그인 성공 **/
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();

        // Resource Owner의 이메일 주소를 얻는다
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));

        // 권한 정보 생성
        List<String> roles = authorityUtils.createRoles(email);

        Member member = makeMember(email, roles, GOOGLE, RandomStringUtils.randomAlphabetic(10));

        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            member = findMember.get();
        } else {
            member = memberRepository.save(member);
        }

        // Access Token과 Refresh Token을 생성해서 Frontend 애플리케이션에 전달하기 위해 Redirect
        redirect(request, response, member, roles);
    }


    /** 주어진 이메일, 권한 정보, provider, 랜덤이름 사용하여 Member 객체 생성 **/
    private Member makeMember(String email, List<String> roles, Member.accountProvider provider,String name) {
        Member member = new Member();
        member.updateEmail(email);
        member.updateRoles(roles);
        member.updateProvider(provider);
        member.updateName(name);
        return member;
    }


    /** 생성된 Access Token과 Refresh Token을 Response 헤더에 포함하여 Frontend 로 리다이렉트 **/
    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member, List<String> authorities) throws IOException {
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        String uri = createURI(request, accessToken, refreshToken).toString();

        // RefreshToken을 DB에 저장
        RefreshToken saveRefreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(refreshToken)
                .build();

        refreshTokenRepository.save(saveRefreshToken);

        String headerValue = "Bearer" + accessToken;
        response.setHeader("Authorization", headerValue);
        response.setHeader("Refresh", refreshToken);

        // Front 쪽으로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, uri);
    }


    /** Access Token 생성 **/
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }


    /** Refresh Token 생성 **/
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }


    /** Redirect URI 생성 **/
    private URI createURI(HttpServletRequest request, String accessToken, String refreshToken) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

//        String serverName = request.getServerName();

        // 프론트로 리다이렉트
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
//                .host("localhost") //-> aws로 배포주소로 변경
                .port(80)   //-> aws로 배포했을 때 사용
                .path("/auth/google") // 리다이렉트 주소 (토큰이 포함된 url 을 받는 주소)
//                Backend 애플리케이션에서 전달받은 JWT Access Token과 Refresh Token을 웹브라우저의 LocalStorage에 저장한 후 화면 이동 전
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}
