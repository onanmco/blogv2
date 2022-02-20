package com.cemonan.blog.dao;

import com.cemonan.blog.domain.User;
import com.cemonan.blog.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDaoImpl implements UserDao {

    private final UserFactory userFactory;

    @Autowired
    public UserDaoImpl(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User create(User user) {
        return user;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User getById(UUID id) {
        return userFactory.create();
    }

    @Override
    public void delete(User user) {

    }
}
