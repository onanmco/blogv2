package com.cemonan.blog.controller;

import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.EmailAlreadyTakenException;
import com.cemonan.blog.exception.NotFoundException;
import com.cemonan.blog.repository.UserRepository;
import com.cemonan.blog.util.BeanUtilsImpl;
import com.cemonan.blog.validation_groups.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PutMapping("/{id}")
    public User update(@PathVariable UUID id, @Validated(UpdateUser.class) @RequestBody User user) {

        User existingUser = userRepository.getById(id);

        if (existingUser == null) {
            throw new NotFoundException("user with id " + id + " not found.");
        }

        if (user.getEmail() != null && userRepository.getByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyTakenException(user.getEmail() + " has already taken.");
        }

        if (user.getEmail() != null || user.getPassword() != null) {
            user.setCredentialsUpdatedAt(OffsetDateTime.now());
        }

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setId(id);
        BeanUtilsImpl.copyNonNullProperties(user, existingUser);
        return userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userRepository.deleteById(id);
    }
}
