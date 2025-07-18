package com.fraud.springprac.api.controller;

import com.fraud.springprac.api.dto.AuthResponseDto;
import com.fraud.springprac.api.dto.LoginDto;
import com.fraud.springprac.api.dto.RegisterDto;
import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.model.Role;
import com.fraud.springprac.api.model.UserEntity;
import com.fraud.springprac.api.repository.ActiveTokenRepository;
import com.fraud.springprac.api.repository.RoleRepository;
import com.fraud.springprac.api.repository.UserRepository;
import com.fraud.springprac.api.security.JWTGenerator;
import com.fraud.springprac.api.security.SecurityConstants;
import com.fraud.springprac.api.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {



    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping("login")
    @Transactional
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

//    @PostMapping("logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        String token = getJWTFromRequest(request);
//
//        if (token != null) {
//            activeTokenRepository.deleteByToken(token);
//            return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("No token found to logout.", HttpStatus.BAD_REQUEST);
//    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}