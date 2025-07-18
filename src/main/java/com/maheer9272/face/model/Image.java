package com.maheer9272.face.model;

public class Image {
    private final String path;
    private final String faceId;

    public Image(String path, String faceId) {
        this.path = path;
        this.faceId = faceId;
    }

    public String getPath() {
        return path;
    }

    public String getFaceId() {
        return faceId;
    }

    @Override
    public String toString() {
        return "Image{path='" + path + "', faceId='" + faceId + "'}";
    }
}