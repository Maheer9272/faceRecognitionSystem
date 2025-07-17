package com.maheer9272.face.model;

import com.clarifai.channel.ClarifaiChannel;
import io.grpc.Channel;
public class Image {
    private final String path;
    private final String faceId;

    public Image(String path, String faceId) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
        if (faceId == null || faceId.isEmpty()) {
            throw new IllegalArgumentException("FaceId cannot be null or empty");
        }
        this.path = path;
        this.faceId = faceId;
    }

    // Getters
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
