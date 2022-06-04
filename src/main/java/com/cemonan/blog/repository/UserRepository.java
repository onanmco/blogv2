package com.cemonan.blog.repository;

import com.cemonan.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Nullable
    User getById(UUID id);

    @Nullable
    User getByEmail(String email);

    User save(User user);

    @Nullable
    void deleteById(UUID id);
}
