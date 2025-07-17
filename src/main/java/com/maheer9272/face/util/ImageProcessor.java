package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageProcessor {
    private final List<Image> images = new ArrayList<>();

    public void loadImages(String resourcePath) throws IOException {
        if (resourcePath == null || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("Resource path cannot be null or empty");
        }
        URL resourceUrl = getClass().getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource path not found: " + resourcePath);
        }
        Path folder = Paths.get(resourceUrl.getPath());
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            throw new IllegalArgumentException("Invalid resource path: " + resourcePath);
        }
        try (var stream = Files.newDirectoryStream(folder, "*.{jpg,JPG}")) {
            for (Path file : stream) {
                String fileName = file.getFileName().toString();
                images.add(new Image("/images/" + fileName, "f" + fileName));
            }
        } catch (IOException e) {
            throw new IOException("Unable to access resource path: " + resourcePath, e);
        }
    }

    public List<Image> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void displayImages() {
        for (Image image : images) {
            System.out.println(image);
        }
    }
}