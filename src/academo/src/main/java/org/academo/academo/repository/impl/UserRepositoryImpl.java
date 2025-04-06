package org.academo.academo.repository.impl;

import org.academo.academo.model.User;
import org.academo.academo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveUser(User model) {
        String sql = "INSERT INTO users(full_name, username, password, role)" + "VALUES (? ,? ,? ,?)";
        jdbcTemplate.update(sql, model.getFullName(), model.getUsername(), model.getPassword(), model.getRole());
    }

    @Override
    public void removeUser(UUID userId) {
        String sql = "UPDATE users SET removed_at = CURRENT_TIMESTAMP WHERE id=?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> getByUserId(UUID userId) {
        String sql = "SELECT * FROM users WHERE id = ? AND removed_at IS NULL";
        return Optional.of(jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId));
    }

    @Override
    public Optional<User> getByUserName(String username) {
        String sql = "SELECT * FROM users WHERE username = ? and removed_at IS NULL";
        return Optional.of(jdbcTemplate.queryForObject(sql, new UserRowMapper(), username));
    }

    @Override
    public Optional<UUID> getIdByUserName(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, UUID.class, username));
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId((UUID) rs.getObject("id"));
            user.setFullName(rs.getString("full_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }
}
