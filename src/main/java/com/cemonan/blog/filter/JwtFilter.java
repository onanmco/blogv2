package com.cemonan.blog.filter;

import com.cemonan.blog.domain.ExceptionResponse;
import com.cemonan.blog.exception.InvalidTokenException;
import com.cemonan.blog.service.JwtService;
import com.cemonan.blog.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");

            if (header == null) {
                throw new InvalidTokenException();
            }

            String prefix = "Bearer ";

            if (!header.startsWith(prefix)) {
                throw new InvalidTokenException();
            }

            String token = header.substring(prefix.length());

            String username = jwtService.extractAllClaims(token).getSubject();


            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isValid(token, userDetails)) {
                throw new InvalidTokenException();
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                throw new InvalidTokenException();
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            ExceptionResponse responseBody = new ExceptionResponse();
            responseBody.setTimestamp(Instant.now().getEpochSecond());
            responseBody.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseBody.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            responseBody.setPath(request.getRequestURI());
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
            response.getWriter().flush();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return Arrays.asList("/login/**", "/register/**").stream()
                .anyMatch(pathPattern -> antPathMatcher.match(pathPattern, request.getRequestURI()));
    }
}
