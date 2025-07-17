package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.List;

public class Eigenfaces {

    public double calculateDistance(Face face1, Face face2) {
        if (face1 == null || face2 == null) {
            throw new IllegalArgumentException("Face objects cannot be null");
        }
        if (face1.getFeatures() == null || face2.getFeatures() == null) {
            throw new IllegalArgumentException("Feature vectors cannot be null");
        }
        if (face1.getFeatures().length != face2.getFeatures().length) {
            throw new IllegalArgumentException("Feature vectors must have equal length");
        }
        ArrayRealVector v1 = new ArrayRealVector(face1.getFeatures());
        ArrayRealVector v2 = new ArrayRealVector(face2.getFeatures());
        return v1.getDistance(v2);// Calculate Euclidean distance between two feature vectors
    }

    public void computePCA(List<Face> faces) {
        if (faces == null || faces.isEmpty()) {
            throw new IllegalArgumentException("Face list cannot be null or empty");
        }
        // Placeholder for PCA computation using Apache Commons Math (RealMatrix for covariance, eigenvalue decomposition)
        // This is where you would implement the PCA logic, such as calculating the covariance matrix,
        System.out.println("Computing PCA for " + faces.size() + " faces...");
    }
}