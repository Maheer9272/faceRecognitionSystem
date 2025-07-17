package com.maheer9272.face.model;

import java.util.*;

public class Face {
    private String id;
    private String name;
    private double[] features; // Feature vector from Clarifai or PCA

    public Face(String id, String name, double[] features) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.features = features.clone(); // Prevent external modification
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return "Face{id='" + id + "', name='" + name + "', features=" + Arrays.toString(features) + "}";
    }
}
