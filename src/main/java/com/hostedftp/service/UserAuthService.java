package com.hostedftp.service;

import com.hostedftp.config.DbConnector;
import com.hostedftp.model.entity.User;
import com.hostedftp.utils.security.PasswordUtil;

import java.sql.*;

public class UserAuthService {
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT id, username, full_name FROM users WHERE username = ?";
        System.out.println("here");
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("full_name")
                    );
                }
                return null;
            }
        }
    }

    public boolean validatePassword(String username, char[] password) throws Exception {
        String sql = "SELECT password_hash, salt FROM users WHERE username = ?";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                byte[] hash = rs.getBytes("password_hash");
                byte[] salt = rs.getBytes("salt");
                return PasswordUtil.verify(password, salt, hash);
            }
        }
    }

    // Utility to create a user (run once to seed)
    public void createUser(String username, String rawPassword, String fullName) throws Exception {
        byte[] salt = PasswordUtil.newSalt();
        byte[] hash = PasswordUtil.hash(rawPassword.toCharArray(), salt);
        String sql = "INSERT INTO users (username, password_hash, salt, full_name) VALUES (?,?,?,?)";
        try (Connection conn = DbConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setBytes(2, hash);
            ps.setBytes(3, salt);
            ps.setString(4, fullName);
            ps.executeUpdate();
        }
    }
}

