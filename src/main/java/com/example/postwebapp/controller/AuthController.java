package com.example.postwebapp.controller;

import com.example.postwebapp.dtos.JWRAuthnResponse;
import com.example.postwebapp.dtos.LoginDto;
import com.example.postwebapp.dtos.RegisterDto;
import com.example.postwebapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = {"/login" , "signin"})
    public ResponseEntity<JWRAuthnResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWRAuthnResponse jwrAuthnResponse= new JWRAuthnResponse();
        jwrAuthnResponse.setAccessToken(token);
        return ResponseEntity.ok(jwrAuthnResponse);
    }
    @PostMapping(value = {"signup" , "register"})
    public ResponseEntity<JWRAuthnResponse> register(@RequestBody RegisterDto registerDto){
        String token = authService.register(registerDto);
        JWRAuthnResponse jwrAuthnResponse= new JWRAuthnResponse();
        jwrAuthnResponse.setAccessToken(token);
        return new ResponseEntity<>(jwrAuthnResponse, HttpStatus.CREATED);
    }
}
