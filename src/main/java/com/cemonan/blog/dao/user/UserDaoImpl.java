package com.cemonan.blog.dao.user;

import com.cemonan.blog.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public User create(User user) {
        String sql = "" +
                "INSERT INTO users (id, first_name, last_name, email, password, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING *;";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    rowMapper,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        String sql = "" +
                "UPDATE users " +
                "SET " +
                "   first_name = ?, " +
                "   last_name = ?, " +
                "   email = ?," +
                "   password = ?," +
                "   updated_at = ? " +
                "WHERE id = ? " +
                "RETURNING *;";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    rowMapper,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getUpdatedAt(),
                    user.getId()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    rowMapper,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?;";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    rowMapper,
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE id = ?;";
        jdbcTemplate.update(sql, user.getId());
    }
}
