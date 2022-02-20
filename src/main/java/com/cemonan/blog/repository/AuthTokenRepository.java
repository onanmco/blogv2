package com.cemonan.blog.repository;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface AuthTokenRepository {
    Token getTokenByUUID(UUID uuid);
    Token create(User user);
    User getUserByToken(Token token);
    Token update(Token token);
    void delete(Token token);
}
