package com.cemonan.blog.domain;

import java.util.UUID;

public class Token {

    private UUID token;
    private Long expiresAt;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
