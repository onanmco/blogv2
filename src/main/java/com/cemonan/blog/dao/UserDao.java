package com.cemonan.blog.dao;

import com.cemonan.blog.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserDao {
    User create(User user);
    User save(User user);
    User getById(UUID id);
    void delete(User user);
}
