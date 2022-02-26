package com.cemonan.blog.factory;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.exception.DALException;

import java.util.List;

public interface TokenFactory {
    Token create() throws DALException;
    List<Token> create(int size) throws DALException;
}
