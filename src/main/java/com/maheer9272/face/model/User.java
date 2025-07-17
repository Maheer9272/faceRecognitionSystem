package com.maheer9272.face.model;

public class User {
    private final int id;
    private final String username;
    private final String faceId;

    public User(int id, String username, String faceId) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (faceId == null || faceId.isEmpty()) {
            throw new IllegalArgumentException("FaceId cannot be null or empty");
        }
        this.id = id;
        this.username = username;
        this.faceId = faceId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFaceId() {
        return faceId;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', faceId='" + faceId + "'}";
    }
}
