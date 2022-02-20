package com.cemonan.blog.factory;

import com.cemonan.blog.domain.Token;

import java.util.List;

public interface TokenFactory {
    Token create();
    List<Token> create(int size);
}
