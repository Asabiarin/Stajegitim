package com.fraud.springprac.api.service.impl;

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
import com.fraud.springprac.api.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final ActiveTokenRepository activeTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JWTGenerator jwtGenerator,
            ActiveTokenRepository activeTokenRepository,
            RedisTemplate<String, String> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.activeTokenRepository = activeTokenRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    @Transactional
    public AuthResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication?"));

        // Clean up old tokens
        activeTokenRepository.deleteByUser(user);
        redisTemplate.delete("user:" + user.getUsername());

        // Generate new token
        String accessToken = jwtGenerator.generateToken(authentication);

        // Store in database
        Date now = new Date();
        ActiveToken activeToken = new ActiveToken(
                accessToken,
                user,
                new Date(now.getTime() + SecurityConstants.JWT_SLIDING_WINDOW_EXPIRATION),
                new Date(now.getTime() + SecurityConstants.JWT_ABSOLUTE_EXPIRATION),
                now);
        activeTokenRepository.save(activeToken);

        // Store in Redis with TTL
        redisTemplate.opsForValue().set(
                "user:" + user.getUsername(),
                accessToken,
                SecurityConstants.JWT_ABSOLUTE_EXPIRATION,
                TimeUnit.MILLISECONDS);

        return new AuthResponseDto(accessToken);
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            ActiveToken activeToken = activeTokenRepository.findByToken(jwt)
                    .orElse(null);
            if (activeToken != null) {
                activeTokenRepository.delete(activeToken);
                redisTemplate.delete("user:" + activeToken.getUser().getUsername());
            }
        }
    }
}