package com.tubes.pbw.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.User;

@Repository
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) throws Exception {
        String sql = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    public void updateUserRole(String email, String role) {
        String sql = "UPDATE users SET role = ? WHERE email = ?";
        jdbcTemplate.update(sql, role, email);
    }

    public void deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    public List<User> findByEmailContaining(String email, int page, int size) {
        String sql = "SELECT * FROM users WHERE email LIKE ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, "%" + email + "%", size, page * size);
    }

    public long countByEmailContaining(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email LIKE ?";
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + email + "%");
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("password"),
                resultSet.getString("role"));
    }

}
