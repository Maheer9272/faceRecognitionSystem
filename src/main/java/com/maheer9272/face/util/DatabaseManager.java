package com.maheer9272.face.util;

import com.maheer9272.face.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String URL = "jdbc:mysql://localhost:3306/face_recognition?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Maheer@@9272";

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("face_id")
                ));
            }
            logger.info("Retrieved {} users from database", users.size());
        } catch (SQLException e) {
            logger.error("Failed to retrieve users from database: {}", e.getMessage(), e);
        }
        return users;
    }
}