package com.maheer9272.face.util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Visualizer extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Visualizer.class);
    private static Map<String, List<FaceDetail>> faceBounds = Collections.emptyMap();
    private static Map<String, String> faceIdToPath = Collections.emptyMap();

    public Visualizer() {
        // JavaFX requires a no-arg constructor
    }

    public static void setFaceBounds(Map<String, List<FaceDetail>> bounds, Map<String, String> idToPath) {
        faceBounds = bounds != null ? bounds : Collections.emptyMap();
        faceIdToPath = idToPath != null ? idToPath : Collections.emptyMap();
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        int xOffset = 0;

        if (faceBounds.isEmpty()) {
            Text msg = new Text(20, 40, "No faces to display. Are demo images present under resources/images?");
            msg.setFill(Color.DARKRED);
            root.getChildren().add(msg);
        }

        for (Map.Entry<String, List<FaceDetail>> entry : faceBounds.entrySet()) {
            String faceId = entry.getKey();
            List<FaceDetail> bounds = entry.getValue();
            String path = faceIdToPath.getOrDefault(faceId, "");

            if (path.isEmpty()) {
                logger.error("No path mapped for faceId {}", faceId);
                continue;
            }

            try (InputStream is = Visualizer.class.getResourceAsStream(path)) {
                if (is == null) {
                    logger.error("Image not found on classpath: {}", path);
                    Text missing = new Text(xOffset + 10, 40, "Missing: " + path);
                    missing.setFill(Color.ORANGERED);
                    root.getChildren().add(missing);
                    xOffset += 300;
                    continue;
                }

                javafx.scene.image.Image image = new javafx.scene.image.Image(is);
                if (image.isError()) {
                    throw new RuntimeException("Failed to load image: " + path);
                }
                ImageView imageView = new ImageView(image);
                imageView.setX(xOffset);
                imageView.setY(0);
                root.getChildren().add(imageView);

                for (FaceDetail face : bounds) {
                    double top = face.boundingBox().top() * image.getHeight();
                    double left = face.boundingBox().left() * image.getWidth();
                    double width = face.boundingBox().width() * image.getWidth();
                    double height = face.boundingBox().height() * image.getHeight();

                    Rectangle rectangle = new Rectangle(
                            xOffset + left,
                            top,
                            width,
                            height
                    );
                    rectangle.setStroke(Color.RED);
                    rectangle.setFill(null);
                    rectangle.setStrokeWidth(2);
                    root.getChildren().add(rectangle);

                    Text label = new Text(
                            xOffset + left,
                            Math.max(0, top - 8),
                            String.format("Conf: %.1f%%", face.confidence())
                    );
                    label.setFill(Color.RED);
                    root.getChildren().add(label);
                }

                xOffset += (int) (image.getWidth() + 10);
            } catch (Exception e) {
                logger.error("Failed to load image for faceId {} at path {}: {}", faceId, path, e.getMessage(), e);
            }
        }

        Scene scene = new Scene(root, Math.max(xOffset, 900), 600);
        primaryStage.setTitle("Face Recognition Results (Demo)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}