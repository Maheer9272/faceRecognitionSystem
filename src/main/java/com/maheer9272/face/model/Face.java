package com.maheer9272.face.model;

import java.util.*;

public class Face {
    private String id;
    private String name;
    private double[] features; // Feature vector from Clarifai or PCA

    public Face(String id, String name, double[] features) {
        this.id = id;
        this.name = name;
        this.features = features;
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
        return "Face{id='" + id + "', name='" + name + "'}";
    }
}
