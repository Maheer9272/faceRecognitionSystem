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


public class Visualizer extends Application {
    private ImageProcessor imageProcessor;

    public Visualizer() {
        this.imageProcessor = new ImageProcessor();
    }

    @Override
    public void start(Stage primaryStage) {
        imageProcessor.loadImages("src/main/resources/images");
        VBox root = new VBox(10);
        Label statusLabel = new Label("Click to load an image");
        Button loadButton = new Button("Load Image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        loadButton.setOnAction(e -> {
            if (!imageProcessor.getImages().isEmpty()) {
                Image firstImage = imageProcessor.getImages().get(0);
                imageView.setImage(new javafx.scene.image.Image("file:" + firstImage.getPath()));
                statusLabel.setText("Loaded: " + firstImage.getFaceId());
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
