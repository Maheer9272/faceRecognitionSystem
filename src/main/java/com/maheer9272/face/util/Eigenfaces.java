package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.List;

public class Eigenfaces {
    public double calculateDistance(Face face1, Face face2) {
        ArrayRealVector v1 = new ArrayRealVector(face1.getFeatures());
        ArrayRealVector v2 = new ArrayRealVector(face2.getFeatures());
        return v1.getDistance(v2); // Euclidean distance for TSCD Stage 2
    }

    public void computePCA(List<Face> faces) {
        // Placeholder for PCA computation (to be implemented on Days 12-15)
        System.out.println("Computing PCA for " + faces.size() + " faces...");
    }
}