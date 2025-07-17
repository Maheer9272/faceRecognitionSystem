package com.maheer9272.face.util;

import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import com.google.protobuf.ByteString;
import com.maheer9272.face.model.Image;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceDetector {
    private static final Logger logger = LoggerFactory.getLogger(FaceDetector.class);
    private final V2Grpc.V2BlockingStub client;
    private final String userId;
    private final String appId;

    public FaceDetector() {
        Dotenv dotenv = Dotenv.load();
        String pat = dotenv.get("CLARIFAI_PAT");
        userId = dotenv.get("CLARIFAI_USER_ID");
        appId = dotenv.get("CLARIFAI_APP_ID");
        if (pat == null || pat.isEmpty() || userId == null || userId.isEmpty() || appId == null || appId.isEmpty()) {
            throw new IllegalStateException("CLARIFAI_PAT, CLARIFAI_USER_ID, and CLARIFAI_APP_ID must be set in .env file");
        }
        client = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials(pat));
    }

    public List<BoundingBox> detectFaces(Image image) throws Exception {
        if (image == null || image.getPath() == null || image.getFaceId() == null) {
            throw new IllegalArgumentException("Image and its properties cannot be null");
        }
        byte[] imageData;
        try {
            imageData = Files.readAllBytes(Paths.get(getClass().getResource(image.getPath()).toURI()));
        } catch (Exception e) {
            logger.error("Failed to read image file: {}", image.getPath(), e);
            throw e;
        }
        MultiOutputResponse response;
        try {
            response = client.postModelOutputs(
                    PostModelOutputsRequest.newBuilder()
                            .setUserAppId(UserAppIdSet.newBuilder().setUserId(userId).setAppId(appId))
                            .setModelId("face-detection")
                            .addInputs(Input.newBuilder().setData(
                                    Data.newBuilder().setImage(com.clarifai.grpc.api.Image.newBuilder().setBase64(ByteString.copyFrom(imageData)))))
                            .build());
        } catch (Exception e) {
            logger.error("Clarifai API call failed for image: {}", image.getFaceId(), e);
            throw e;
        }
        List<BoundingBox> boundingBoxes = new ArrayList<>();
        for (Output output : response.getOutputsList()) {
            for (Region region : output.getData().getRegionsList()) {
                BoundingBox box = region.getRegionInfo().getBoundingBox();
                boundingBoxes.add(box);
                logger.info("Face detected in {}: Top={}, Left={}, Bottom={}, Right={}",
                        image.getFaceId(), box.getTopRow(), box.getLeftCol(), box.getBottomRow(), box.getRightCol());
            }
        }
        if (boundingBoxes.isEmpty()) {
            logger.warn("No faces detected in {}", image.getFaceId());
        }
        return boundingBoxes;
    }
}