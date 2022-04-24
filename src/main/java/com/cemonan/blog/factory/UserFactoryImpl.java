package com.cemonan.blog.factory;

import com.cemonan.blog.domain.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@ComponentScan(basePackages = {"com.cemonan.blog.config"})
public class UserFactoryImpl implements UserFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public UserFactoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public User create() {
        Faker faker = getFakerInstance();
        return createUser(faker);
    }

    @Override
    public List<User> create(int size) {
        Faker faker = getFakerInstance();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            users.add(createUser(faker));
        }
        return users;
    }

    private User createUser(Faker faker) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        OffsetDateTime now = new Date();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return user;
    }

    private Faker getFakerInstance() {
        return (Faker) applicationContext.getBean("config.FakerConfig.getFakerInstance");
    }
}
