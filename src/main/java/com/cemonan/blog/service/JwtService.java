package com.cemonan.blog.service;

import com.cemonan.blog.domain.User;
import com.cemonan.blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String SECRET_KEY = "123456789012345";

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean isValid(String token, UserDetails userDetails) {
        try {
            String username = extractAllClaims(token).getSubject();
            Date iat = extractAllClaims(token).getIssuedAt();
            Date exp = extractAllClaims(token).getExpiration();

            if (username == null || iat == null || exp == null) {
                return false;
            }

            if (!username.equals(userDetails.getUsername())) {
                return false;
            }

            if (exp.before(new Date())) {
                return false;
            }

            User user = userRepository.getByEmail(username);

            if (user == null) {
                return false;
            }

            if (iat.before(new Date(user.getCredentialsUpdatedAt().toInstant().toEpochMilli()))) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}