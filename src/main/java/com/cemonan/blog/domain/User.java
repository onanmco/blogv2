package com.cemonan.blog.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

@Component
@Validated
public class User {

    private UUID id;

    @NotBlank(message = "firstName cannot be empty.")
    @Size(min = 1, message = "firstName cannot be less than 1 character.")
    @Size(max = 255, message = "firstName cannot be longer than 255 characters.")
    private String firstName;

    @NotBlank(message = "lastName cannot be empty.")
    @Size(min = 1, message = "lastName cannot be less than 1 character.")
    @Size(max = 255, message = "lastName cannot be longer than 255 characters.")
    private String lastName;

    @NotBlank(message = "email cannot be empty.")
    @Email(message = "email should be a valid email address.")
    private String email;

    @NotBlank(message = "password cannot be empty.")
    @Size(min = 8, message = "password cannot be less than 8 characters.")
    @Size(max = 32, message = "password cannot be longer than 32 characters.")
    private String password;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
