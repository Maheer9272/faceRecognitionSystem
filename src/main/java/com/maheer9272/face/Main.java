package com.maheer9272.face;

import com.maheer9272.face.model.Face;
import com.maheer9272.face.model.Image;
import com.maheer9272.face.util.FaceManager;
import com.maheer9272.face.util.ImageProcessor;
import com.maheer9272.face.util.FaceDetector;
import com.maheer9272.face.util.DatabaseManager;
import com.maheer9272.face.util.Visualizer;

public class Main {
    public static void main(String[] args) throws Exception {
        // Initialize FaceManager
        FaceManager manager = new FaceManager();
        manager.addFace(new Face("f1", "BOB", new double[]{1.0, 2.0}));
        manager.addFace(new Face("f2", "Alice", new double[]{3.0, 4.0}));
        System.out.println("Faces:");
        manager.displayFaces();

        // Initialize ImageProcessor
        ImageProcessor processor = new ImageProcessor();
        processor.loadImages("src/main/resources/images");
        System.out.println("\nImages:");
        processor.displayImages();

        // Initialize FaceDetector
        FaceDetector detector = new FaceDetector();
        System.out.println("\nDetecting faces:");
        for (Image image : processor.getImages()) {
            detector.detectFaces(image);
        }

        // Initialize DatabaseManager
        DatabaseManager dbManager = new DatabaseManager();
        System.out.println("\nUsers in database:");
        dbManager.displayUsers();

        // Launch JavaFX frontend
        Visualizer.main(args);
    }
}