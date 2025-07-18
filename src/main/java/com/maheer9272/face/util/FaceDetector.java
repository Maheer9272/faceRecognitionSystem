package com.maheer9272.face.util;

import com.maheer9272.face.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectFacesRequest;
import software.amazon.awssdk.services.rekognition.model.DetectFacesResponse;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FaceDetector {
    private static final Logger logger = LoggerFactory.getLogger(FaceDetector.class);
    private final RekognitionClient client;

    public FaceDetector() {
        client = RekognitionClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        logger.info("Initialized RekognitionClient for region US_EAST_1");
    }

    public List<FaceDetail> detectFaces(Image image) throws Exception {
        if (image == null || image.getPath() == null || image.getFaceId() == null) {
            throw new IllegalArgumentException("Image and its properties cannot be null");
        }

        byte[] imageData;
        try (InputStream inputStream = getClass().getResourceAsStream(image.getPath())) {
            if (inputStream == null) {
                throw new RuntimeException("Image resource not found: " + image.getPath());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            imageData = outputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Failed to read image file: {}", image.getPath(), e);
            throw new RuntimeException("Failed to read image: " + image.getPath(), e);
        }

        List<FaceDetail> boundingBoxes = new ArrayList<>();
        try {
            DetectFacesRequest request = DetectFacesRequest.builder()
                    .image(software.amazon.awssdk.services.rekognition.model.Image.builder()
                            .bytes(SdkBytes.fromByteArray(imageData))
                            .build())
                    .build();
            DetectFacesResponse response = client.detectFaces(request);
            boundingBoxes = response.faceDetails();
            for (FaceDetail face : boundingBoxes) {
                logger.info("Face detected in {}: Confidence={}, Top={}, Left={}, Width={}, Height={}",
                        image.getFaceId(),
                        face.confidence(),
                        face.boundingBox().top(),
                        face.boundingBox().left(),
                        face.boundingBox().width(),
                        face.boundingBox().height());
            }
            if (boundingBoxes.isEmpty()) {
                logger.warn("No faces detected in {}", image.getFaceId());
            }
        } catch (Exception e) {
            logger.error("Rekognition API call failed for image {}: {}", image.getFaceId(), e.getMessage(), e);
            throw new RuntimeException("Rekognition API call failed", e);
        }
        return boundingBoxes;
    }
}