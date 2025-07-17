package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    private final List<Image> images = new ArrayList<>();

    public void loadImages(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.getName().toLowerCase().endsWith(".jpg")) {
                    images.add(new Image(file.getAbsolutePath(), "f" + file.getName()));
                }
            }
        }
    }

    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

    public void displayImages() {
        for (Image image : images) {
            System.out.println(image);
        }
    }
}
