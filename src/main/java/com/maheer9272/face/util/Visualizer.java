package com.maheer9272.face.util;

import com.clarifai.grpc.api.BoundingBox;
import com.maheer9272.face.model.Image;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Visualizer extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Visualizer.class);
    private List<Image> images = new ArrayList<>();
    private Map<String, List<BoundingBox>> faceBounds;
    private int currentIndex = 0;
    private ImageView imageView;

    public void setImages(List<Image> images, Map<String, List<BoundingBox>> faceBounds) {
        this.images = images != null ? images : new ArrayList<>();
        this.faceBounds = faceBounds;
    }

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        Button nextButton = new Button("Load Next Image");
        nextButton.setOnAction(e -> loadNextImage());

        VBox root = new VBox(10, imageView, nextButton);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Face Recognition");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (!images.isEmpty()) {
            loadNextImage();
        } else {
            logger.warn("No images provided to Visualizer");
        }
    }

    private void loadNextImage() {
        if (images.isEmpty()) {
            logger.warn("No images to display");
            return;
        }
        currentIndex = (currentIndex + 1) % images.size();
        Image currentImage = images.get(currentIndex);
        try {
            imageView.setImage(new javafx.scene.image.Image(getClass().getResource(currentImage.getPath()).toString()));
            // Draw bounding boxes
            VBox root = (VBox) imageView.getParent();
            root.getChildren().removeIf(node -> node instanceof Rectangle);
            if (faceBounds != null && faceBounds.containsKey(currentImage.getFaceId())) {
                for (BoundingBox box : faceBounds.get(currentImage.getFaceId())) {
                    double x = box.getLeftCol() * imageView.getFitWidth();
                    double y = box.getTopRow() * imageView.getFitHeight();
                    double width = (box.getRightCol() - box.getLeftCol()) * imageView.getFitWidth();
                    double height = (box.getBottomRow() - box.getTopRow()) * imageView.getFitHeight();
                    Rectangle rect = new Rectangle(x, y, width, height);
                    rect.setFill(null);
                    rect.setStroke(Color.RED);
                    rect.setStrokeWidth(2);
                    root.getChildren().add(rect);
                }
            }
            logger.info("Loaded image: {}", currentImage.getPath());
        } catch (Exception e) {
            logger.error("Failed to load image: {}", currentImage.getPath(), e);
        }
    }
}