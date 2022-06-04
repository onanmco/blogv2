package com.cemonan.blog.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "email is required.")
    @Email(message = "email must be a valid email address.")
    private String email;

    @NotEmpty(message = "password is required.")
    private String password;
}
