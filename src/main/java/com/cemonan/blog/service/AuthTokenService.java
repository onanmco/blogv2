package com.cemonan.blog.service;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;

public interface AuthTokenService {
    Token getAuthUsersToken();
    User getAuthUser();
    Token create(User user);
    Token extendTokensExpiration(Token token);
}
