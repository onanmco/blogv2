package com.cemonan.blog.dao.user;

import com.cemonan.blog.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface UserDao {
    User getById(UUID id);
    User getByEmail(String email);
    User create(User user);
    User update(User user);
    void delete(User user);
}
