package com.skuplate.server.auth.controller;

import com.skuplate.server.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    /** Access token 재발급 **/
    @PostMapping("/reissue")
    public ResponseEntity reissue (HttpServletRequest request, HttpServletResponse response) {

        authService.reissue(request, response);
        return ResponseEntity.ok().build();
    }

    /** 로그아웃 **/
    @DeleteMapping("/logout")
    public ResponseEntity logout (HttpServletRequest request) {

        authService.logout(request);
        return ResponseEntity.ok().build();
    }
}