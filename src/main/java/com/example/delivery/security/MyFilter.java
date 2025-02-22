package com.example.delivery.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class MyFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final CustomUserDetailService customUserDetailService;
    public MyFilter(TokenService tokenService,
                    CustomUserDetailService customUserDetailService) {
        this.tokenService = tokenService;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if (token != null) {
            if (tokenService.validate(token)) {
               final String username = tokenService.getUserName(token);
                List<SimpleGrantedAuthority> roles = tokenService.getRoles(token);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                final var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        roles
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }


}
