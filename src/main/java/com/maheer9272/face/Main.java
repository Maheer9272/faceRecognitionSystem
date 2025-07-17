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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialize FaceManager
        FaceManager manager = new FaceManager();
        try {
            manager.addFace(new Face("f1", "BOB", new double[]{1.0, 2.0}));
            manager.addFace(new Face("f2", "Alice", new double[]{3.0, 4.0}));
            logger.info("Faces:");
            manager.displayFaces();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add faces: {}", e.getMessage());
            return;
        }

        // Initialize ImageProcessor
        ImageProcessor processor = new ImageProcessor();
        List<Image> images;
        try {
            processor.loadImages("/images");
            images = processor.getImages();
            logger.info("Images:");
            processor.displayImages();
        } catch (IOException e) {
            logger.error("Failed to load images: {}", e.getMessage());
            return;
        }

        // Initialize FaceDetector
        FaceDetector detector = new FaceDetector();
        Map<String, List<BoundingBox>> faceBounds = new HashMap<>();
        try {
            logger.info("Detecting faces:");
            for (Image image : images) {
                List<BoundingBox> bounds = detector.detectFaces(image);
                faceBounds.put(image.getFaceId(), bounds);
            }
        } catch (Exception e) {
            logger.error("Failed to detect faces: {}", e.getMessage());
            return;
        }

        // Initialize DatabaseManager
        DatabaseManager dbManager = new DatabaseManager();
        try {
            logger.info("Users in database:");
            dbManager.displayUsers();
        } catch (SQLException e) {
            logger.error("Failed to access database: {}", e.getMessage());
            return;
        }

        // Launch JavaFX frontend
        try {
            Visualizer visualizer = new Visualizer();
            visualizer.setImages(images, faceBounds);
            Application.launch(Visualizer.class, args);
        } catch (Exception e) {
            logger.error("Failed to launch JavaFX application: {}", e.getMessage());
        }
    }
}