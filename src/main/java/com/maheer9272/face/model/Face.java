package com.maheer9272.face.model;

import java.util.Arrays;

public class Face {
    private final String id;
    private final String name;
    private final double[] features;

    public Face(String id, String name, double[] features) {
        this.id = id;
        this.name = name;
        this.features = features != null ? features : new double[0];
    }

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