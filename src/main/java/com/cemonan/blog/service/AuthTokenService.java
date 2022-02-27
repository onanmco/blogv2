package com.cemonan.blog.service;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.DALException;

public interface AuthTokenService {
    Token getAuthUsersToken();
    User getAuthUser();
    Token create(User user) throws DALException;
    Token extendTokensExpiration(Token token) throws DALException;
}
