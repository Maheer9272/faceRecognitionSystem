package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageProcessor {
    private final List<Image> images = new ArrayList<>();

    public void loadImages(String folderPath) throws IllegalArgumentException {
        if (folderPath == null || folderPath.isEmpty()) {
            throw new IllegalArgumentException("Folder path cannot be null or empty");
        }
        File folder = new File(getClass().getResource(folderPath).getFile());
        // Rest of the code
    }

    public List<Image> getImages() {
        return List.copyOf(images);
    }

    public void displayImages() {
        for (Image image : images) {
            System.out.println(image);
        }
    }
}
