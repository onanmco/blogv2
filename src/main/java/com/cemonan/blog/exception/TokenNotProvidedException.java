package com.cemonan.blog.exception;

public class TokenNotProvidedException extends RuntimeException {
    public TokenNotProvidedException(String message) {
        super(message);
    }
}
