package com.tubes.pbw.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.User;

@Repository
public class memberRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save a new user
    public void save(User user) {
        String sql = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getUsername(), user.getPassword(), user.getRole());
    }

    // Find user by ID
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> results = jdbcTemplate.query(sql, this::mapRowToUser, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // Find all users
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    // Update user role
    public void updateUserRole(int id, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        jdbcTemplate.update(sql, role, id);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Map database row to User object
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("password"), // Placeholder for confirmPassword
                resultSet.getString("role"));
    }
}
