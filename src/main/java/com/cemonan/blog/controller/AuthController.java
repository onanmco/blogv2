package com.cemonan.blog.controller;

import com.cemonan.blog.domain.LoginRequest;
import com.cemonan.blog.domain.LoginResponse;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.EmailAlreadyTakenException;
import com.cemonan.blog.repository.UserRepository;
import com.cemonan.blog.service.JwtService;
import com.cemonan.blog.service.UserDetailsServiceImpl;
import com.cemonan.blog.validation_groups.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtService.generateToken(userDetails));
        return response;
    }

    @PostMapping("/register")
    public User register(@Validated(CreateUser.class) @RequestBody User user) {
        User existingUser = userRepository.getByEmail(user.getEmail());

        if (existingUser != null) {
            throw new EmailAlreadyTakenException(user.getEmail() + " has already taken.");
        }

        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setCredentialsUpdatedAt(OffsetDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
