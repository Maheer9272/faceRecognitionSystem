package com.maheer9272.face.util;

import com.maheer9272.face.model.User;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/FaceDB";
    private static final String USER;
    private static final String PASSWORD;
    static {
        Dotenv dotenv = Dotenv.load();
        USER = dotenv.get("DB_USER", "root"); // Fallback to "root"
        PASSWORD = dotenv.get("DB_PASSWORD", "Maheer@@9272"); // Fallback to your password
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, face_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFaceId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw to notify caller of failure
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("face_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void displayUsers() throws SQLException {
        for (User user : getAllUsers()) {
            System.out.println(user);
        }
    }
}