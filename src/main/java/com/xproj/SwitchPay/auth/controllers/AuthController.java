package com.xproj.SwitchPay.auth.controllers;

import com.xproj.SwitchPay.auth.dtos.LoginRequestDto;
import com.xproj.SwitchPay.auth.dtos.RegisterRequestDto;
import com.xproj.SwitchPay.auth.service.AuthService;
import com.xproj.SwitchPay.utils.ApplicationResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponseWrapper<Map<String, String>>> login (@RequestBody LoginRequestDto loginRequestDto) {
        //TODO: add validation for login

        Map<String, String> response = authService.signInUser(loginRequestDto);
        ApplicationResponseWrapper<Map<String, String>> applicationResponseWrapper = new ApplicationResponseWrapper<>("success", response);
        return ResponseEntity.ok(applicationResponseWrapper);
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponseWrapper<Map<String, String>>> signUpUser (@RequestBody RegisterRequestDto registerRequestDto) {
        //TODO: add validation for register

        Map<String, String> response = authService.registerUser(registerRequestDto);
        ApplicationResponseWrapper<Map<String, String>> applicationResponseWrapper = new ApplicationResponseWrapper<>("success", response);
        return ResponseEntity.ok(applicationResponseWrapper);
    }
}
