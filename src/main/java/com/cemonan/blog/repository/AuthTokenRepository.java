package com.cemonan.blog.repository;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;

import java.util.UUID;

public interface AuthTokenRepository {
    Token getTokenByUUID(UUID uuid);
    Token create(User user);
    User getUserByToken(Token token);
    Token update(Token token);
    void delete(Token token);
}
