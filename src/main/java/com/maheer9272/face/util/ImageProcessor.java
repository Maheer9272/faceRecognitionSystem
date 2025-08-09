package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

    // Provide a small default image set for demo mode.
    // Place files under src/main/resources/images/
    public List<Image> getImages() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("/images/image1.jpg", "img-1"));
        images.add(new Image("/images/image2.jpg", "img-2"));
        images.add(new Image("/images/image3.jpg", "img-3"));
        logger.info("Retrieved {} demo images", images.size());
        return images;
    }
}