package com.maheer9272.face.model;

public class User {
    private final int id;
    private final String username;
    private final String faceId;

    public User(int id, String username, String faceId) {
        this.id = id;
        this.username = username;
        this.faceId = faceId;
    }

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