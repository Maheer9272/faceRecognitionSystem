package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

    public List<Image> getImages() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("/images/image1.jpg", "fimage1.jpg"));
        images.add(new Image("/images/image2.jpg", "fimage2.jpg"));
        images.add(new Image("/images/image3.jpg", "fimage3.jpg"));
        images.add(new Image("/images/image4.jpg", "fimage4.jpg"));
        images.add(new Image("/images/image5.jpg", "fimage5.jpg"));
        logger.info("Retrieved {} images", images.size());
        return images;
    }
}