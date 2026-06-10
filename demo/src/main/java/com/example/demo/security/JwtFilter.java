package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.
        SecurityContextHolder;
import org.springframework.security.core.userdetails.
        UserDetails;
import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);

            if (JwtUtil.validateToken(jwt)) {
                username = JwtUtil.extractUsername(jwt);
            }
        }

        if (username != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);   //This request is authenticated
        }

        filterChain.doFilter(request, response);  //Passes request forward
    }
}