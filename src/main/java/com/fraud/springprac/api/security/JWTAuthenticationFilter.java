package com.fraud.springprac.api.security;

import com.fraud.springprac.api.model.ActiveToken;
import com.fraud.springprac.api.repository.ActiveTokenRepository;
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
import java.util.Date;


public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ActiveTokenRepository activeTokenRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token)) {
            if (!tokenGenerator.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            ActiveToken activeToken = activeTokenRepository.findByToken(token).orElse(null);

            if (activeToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            Date now = new Date();

            if (now.after(activeToken.getAbsoluteExpiration())){
                activeTokenRepository.delete(activeToken);
                filterChain.doFilter(request, response);
                return;
            }

            if (now.after(activeToken.getSlidingExpiration())){
                activeTokenRepository.delete(activeToken);
                filterChain.doFilter(request, response);
                return;
            }

            Date newSlidingExpiration = new Date(now.getTime() + SecurityConstants.JWT_SLIDING_WINDOW_EXPIRATION);
            activeToken.setSlidingExpiration(newSlidingExpiration);
            activeTokenRepository.save(activeToken);

            String username = activeToken.getUser().getUsername();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
       String bearerToken = request.getHeader("Authorization");
       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7,  bearerToken.length());
       }
       return null;
    }
}
