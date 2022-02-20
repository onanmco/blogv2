package com.cemonan.blog.factory;

import com.cemonan.blog.domain.User;

import java.util.List;

public interface UserFactory {
    User create();
    List<User> create(int size);
}
