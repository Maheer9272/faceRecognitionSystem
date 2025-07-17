package com.maheer9272.face;

import com.clarifai.grpc.api.BoundingBox;
import com.maheer9272.face.model.Face;
import com.maheer9272.face.model.Image;
import com.maheer9272.face.util.FaceManager;
import com.maheer9272.face.util.ImageProcessor;
import com.maheer9272.face.util.FaceDetector;
import com.maheer9272.face.util.DatabaseManager;
import com.maheer9272.face.util.Visualizer;
import javafx.application.Application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Initialize FaceManager
        FaceManager manager = new FaceManager();
        try {
            manager.addFace(new Face("f1", "BOB", new double[]{1.0, 2.0}));
            manager.addFace(new Face("f2", "Alice", new double[]{3.0, 4.0}));
            System.out.println("Faces:");
            manager.displayFaces();
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to add faces: " + e.getMessage());
            return;
        }

        // Initialize ImageProcessor
        ImageProcessor processor = new ImageProcessor();
        try {
            processor.loadImages("/images");
            System.out.println("\nImages:");
            processor.displayImages();
        } catch (IOException e) {
            System.err.println("Failed to load images: " + e.getMessage());
            return;
        }

        // Initialize FaceDetector
        FaceDetector detector = new FaceDetector();
        Map<String, List<BoundingBox>> faceBounds = new HashMap<>();
        try {
            System.out.println("\nDetecting faces:");
            for (Image image : processor.getImages()) {
                List<BoundingBox> bounds = detector.detectFaces(image);
                faceBounds.put(image.getFaceId(), bounds);
            }
        } catch (Exception e) {
            System.err.println("Failed to detect faces: " + e.getMessage());
            return;
        }

        // Initialize DatabaseManager
        DatabaseManager dbManager = new DatabaseManager();
        try {
            System.out.println("\nUsers in database:");
            dbManager.displayUsers();
        } catch (SQLException e) {
            System.err.println("Failed to access database: " + e.getMessage());
            return;
        }

        // Launch JavaFX frontend
        try {
            Application.launch(Visualizer.class, args);
        } catch (Exception e) {
            System.err.println("Failed to launch JavaFX application: " + e.getMessage());
        }
    }
}