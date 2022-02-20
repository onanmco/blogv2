package com.cemonan.blog.exception;

public class AuthUserNotFoundException extends RuntimeException {
    public AuthUserNotFoundException(String message) {
        super(message);
    }
}
