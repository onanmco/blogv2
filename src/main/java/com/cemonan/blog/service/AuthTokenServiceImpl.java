package com.cemonan.blog.service;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.DALException;
import com.cemonan.blog.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.time.Instant;

@Component
public class AuthTokenServiceImpl implements AuthTokenService {

    private final WebApplicationContext webApplicationContext;

    private final AuthTokenRepository authTokenRepository;

    @Value("${token.expires.after.seconds}")
    private String DEFAULT_EXPIRATION;

    @Autowired
    public AuthTokenServiceImpl(WebApplicationContext webApplicationContext, AuthTokenRepository authTokenRepository) {
        this.webApplicationContext = webApplicationContext;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public Token getAuthUsersToken() {
        return (Token) getContext().getAttribute("auth.token");
    }

    @Override
    public User getAuthUser() {
        return (User) getContext().getAttribute("auth.user");
    }

    @Override
    public Token create(User user) throws DALException {
        return authTokenRepository.create(user);
    }

    @Override
    public Token extendTokensExpiration(Token token) throws DALException {
        token.setExpiresAt(Instant.now().getEpochSecond() + Long.valueOf(DEFAULT_EXPIRATION));
        return authTokenRepository.update(token);
    }

    private ServletContext getContext() {
        return webApplicationContext.getServletContext();
    }
}
