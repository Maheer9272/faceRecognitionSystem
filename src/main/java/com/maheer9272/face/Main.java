package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import com.maheer9272.face.model.Image;
import com.maheer9272.face.model.User;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting face recognition system...");

            FaceManager faceManager = new FaceManager();
            ImageProcessor imageProcessor = new ImageProcessor();
            FaceDetector faceDetector = new FaceDetector();
            DatabaseManager dbManager = new DatabaseManager();

            List<Face> faces = faceManager.getFaces();
            logger.info("Faces retrieved:");
            faces.forEach(face -> logger.info("{}", face));

            List<Image> images = imageProcessor.getImages();
            logger.info("Images retrieved:");
            images.forEach(image -> logger.info("{}", image));

            Map<String, List<FaceDetail>> faceBounds = new HashMap<>();
            Map<String, String> faceIdToPath = new HashMap<>();
            logger.info("Detecting faces in images...");
            for (Image image : images) {
                try {
                    List<FaceDetail> bounds = faceDetector.detectFaces(image);
                    faceBounds.put(image.getFaceId(), bounds);
                    faceIdToPath.put(image.getFaceId(), image.getPath());
                } catch (Exception e) {
                    logger.error("Failed to detect faces in image {}: {}", image.getFaceId(), e.getMessage());
                }
            }

            List<User> users = dbManager.getUsers();
            logger.info("Users in database:");
            users.forEach(user -> logger.info("{}", user));

            Visualizer.setFaceBounds(faceBounds, faceIdToPath);
            Application.launch(Visualizer.class);

        } catch (Exception e) {
            logger.error("Application failed: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}