package com.cemonan.blog.factory;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenFactoryImpl implements TokenFactory {

    private final UserFactory userFactory;
    private final AuthTokenRepository authTokenRepository;

    @Autowired
    public TokenFactoryImpl(UserFactory userFactory, AuthTokenRepository authTokenRepository) {
        this.userFactory = userFactory;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public Token create() {
        User user = userFactory.create();
        Token token = authTokenRepository.create(user);
        return token;
    }

    @Override
    public List<Token> create(int size) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tokens.add(create());
        }
        return tokens;
    }
}
