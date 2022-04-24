package com.cemonan.blog.dao.user;

import com.cemonan.blog.domain.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class RowMapper implements org.springframework.jdbc.core.RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(UUID.fromString(rs.getString("id")));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        user.setUpdatedAt(rs.getObject("updated_at", OffsetDateTime.class));

        return user;
    }
}
