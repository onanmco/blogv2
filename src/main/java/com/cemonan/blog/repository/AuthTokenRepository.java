package com.cemonan.blog.repository;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.DALException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface AuthTokenRepository {
    Token getTokenByUUID(UUID uuid) throws DALException;
    Token create(User user) throws DALException;
    User getUserByToken(Token token) throws DALException;
    Token update(Token token) throws DALException;
    void delete(Token token) throws DALException;
}
