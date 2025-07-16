package com.fraud.springprac.api.service;

import com.fraud.springprac.api.dto.AuthResponseDto;
import com.fraud.springprac.api.dto.LoginDto;
import com.fraud.springprac.api.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    AuthResponseDto login(LoginDto loginDto);
    void logout(String token);
}