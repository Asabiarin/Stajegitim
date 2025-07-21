package com.fraud.springprac.api.security;

import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.repository.ActiveTokenRepository;
import com.fraud.springprac.api.service.impl.RedisServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private ActiveTokenRepository activeTokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
//        System.out.println("Entering the JWT filter: " + DateMilliStamp.timeStamp());
        String token = getJWTFromRequest(request);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
//        System.out.println("Leaving the first if : " + DateMilliStamp.timeStamp());
        // Step 1: Validate JWT signature
        if (!tokenGenerator.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
//        System.out.println("Leaving the validateToken " + DateMilliStamp.timeStamp());
        // Step 2: Check Redis for valid token (checks both expirations)
       // Optional<ActiveToken> activeTokenOpt = redisService.findValidToken(token);
//        if (activeTokenOpt.isEmpty()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        String username = tokenGenerator.getUsernameFromJWT(token);
        // Step 3: Extend sliding expiration if token is still valid
        //System.out.println("Before the IfNotExpired: " + DateMilliStamp.timeStamp());
        if (!redisService.extendTokenIfNotExpired(username, SecurityConstants.JWT_SLIDING_WINDOW_EXPIRATION)) {
            filterChain.doFilter(request, response);
            return;
        }
//        System.out.println("After the extendTokenIfNotExpired " + DateMilliStamp.timeStamp());
        // Step 4: Set authentication
        //ActiveToken activeToken = activeTokenOpt.get();
//        Date now = new Date();

        updateDatabaseToken(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(tokenGenerator.getUsernameFromJWT(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);


//        System.out.println("Leaving the authentication " + DateMilliStamp.timeStamp());
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    private void updateDatabaseToken(String token) {
        ActiveToken activeToken = activeTokenRepository.findByToken(token);

        Instant newSlidingExpiry = Instant.now()
                .plusMillis(SecurityConstants.JWT_SLIDING_WINDOW_EXPIRATION);

        if (newSlidingExpiry.isBefore(activeToken.getAbsoluteExpiration().toInstant())) {
            activeToken.setSlidingExpiration(Date.from(newSlidingExpiry));
            activeTokenRepository.save(activeToken);
        }
    }
}