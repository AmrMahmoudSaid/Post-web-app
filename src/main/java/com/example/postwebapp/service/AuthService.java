package com.example.postwebapp.service;

import com.example.postwebapp.dtos.LoginDto;
import com.example.postwebapp.dtos.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register (RegisterDto registerDto);
}
