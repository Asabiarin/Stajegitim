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

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final ActiveTokenRepository  activeTokenRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator ,ActiveTokenRepository activeTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.activeTokenRepository = activeTokenRepository;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping("login")
    @Transactional
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication?"));

        activeTokenRepository.deleteByUser(user);

        String accessToken = jwtGenerator.generateToken(authentication);

        Date now = new Date();
        Date slidingExp = new Date(now.getTime() + SecurityConstants.JWT_SLIDING_WINDOW_EXPIRATION);
        Date absoluteExp = new Date(now.getTime() + SecurityConstants.JWT_ABSOLUTE_EXPIRATION);

        ActiveToken activeToken = new ActiveToken(accessToken, user, slidingExp, absoluteExp, now);
        activeTokenRepository.save(activeToken);

        return new ResponseEntity<>(new AuthResponseDto(accessToken), HttpStatus.OK);

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