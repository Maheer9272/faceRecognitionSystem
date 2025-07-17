package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import com.maheer9272.face.util.ImageProcessor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class Visualizer extends Application {
    private ImageProcessor imageProcessor;
    private int currentImageIndex = 0; // Track current image

    @Override
    public void start(Stage primaryStage) {
        imageProcessor = new ImageProcessor();
        try {
            imageProcessor.loadImages("src/main/resources/images"); // Use direct path for development
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to load images: " + e.getMessage());
            return; // Exit early if image loading fails
        }

        VBox root = new VBox(10);
        Label statusLabel = new Label("Click to load an image");
        Button loadButton = new Button("Load Next Image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        loadButton.setOnAction(e -> {
            List<Image> images = imageProcessor.getImages();
            if (!images.isEmpty()) {
                Image currentImage = images.get(currentImageIndex);
                try {
                    imageView.setImage(new javafx.scene.image.Image("file:" + currentImage.getPath()));
                    statusLabel.setText("Loaded: " + currentImage.getFaceId());
                    currentImageIndex = (currentImageIndex + 1) % images.size(); // Cycle through images
                } catch (Exception ex) {
                    statusLabel.setText("Error loading image: " + ex.getMessage());
                }
            } else {
                statusLabel.setText("No images found");
            }
        });

        root.getChildren().addAll(statusLabel, loadButton, imageView);
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Face Recognition");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}