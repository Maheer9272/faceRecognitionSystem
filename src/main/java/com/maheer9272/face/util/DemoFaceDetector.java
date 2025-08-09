package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.rekognition.model.BoundingBox;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates synthetic face detections so the UI works without AWS creds.
 */
public class DemoFaceDetector {
    private static final Logger logger = LoggerFactory.getLogger(DemoFaceDetector.class);

    public List<FaceDetail> detectFaces(Image image) {
        List<FaceDetail> faces = new ArrayList<>();

        // Two fake faces with stable positions to visualize
        faces.add(FaceDetail.builder()
                .confidence(97.2f)
                .boundingBox(BoundingBox.builder()
                        .top(0.15f).left(0.12f).width(0.25f).height(0.35f)
                        .build())
                .build());
        faces.add(FaceDetail.builder()
                .confidence(92.6f)
                .boundingBox(BoundingBox.builder()
                        .top(0.30f).left(0.55f).width(0.25f).height(0.35f)
                        .build())
                .build());

        logger.info("Demo detector produced {} fake faces for {}", faces.size(), image.getFaceId());
        return faces;
    }
}