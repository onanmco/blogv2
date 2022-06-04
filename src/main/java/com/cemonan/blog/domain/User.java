package com.cemonan.blog.domain;

import com.cemonan.blog.validation_groups.CreateUser;
import com.cemonan.blog.validation_groups.UpdateUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "first_name is required.", groups = {CreateUser.class})
    @Size(min = 2, message = "first_name field should be at least 2 characters length.", groups = {CreateUser.class, UpdateUser.class})
    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "last_name is required.", groups = {CreateUser.class})
    @Size(min = 2, message = "last_name field should be at least 2 characters length.", groups = {CreateUser.class, UpdateUser.class})
    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "email is required.", groups = {CreateUser.class})
    @Email(message = "email must be valid e-mail address.", groups = {CreateUser.class, UpdateUser.class})
    private String email;

    @NotNull(message = "password is required.", groups = {CreateUser.class})
    @Size(min = 8, message = "password must be at least 8 characters long.", groups = {CreateUser.class, UpdateUser.class})
    @Size(max = 16, message = "password must be at most 16 characters long.", groups = {CreateUser.class, UpdateUser.class})
    @Pattern(regexp = ".*[0-9].*", message = "password must have at least 1 digit.", groups = {CreateUser.class, UpdateUser.class})
    @Pattern(regexp = ".*[A-Z].*", message = "password must have at least 1 uppercase letter.", groups = {CreateUser.class, UpdateUser.class})
    @Pattern(regexp = ".*[a-z].*", message = "password must have at least 1 lowercase letter.", groups = {CreateUser.class, UpdateUser.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "credentials_updated_at")
    @JsonProperty(value = "credentials_updated_at", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime credentialsUpdatedAt;
}
