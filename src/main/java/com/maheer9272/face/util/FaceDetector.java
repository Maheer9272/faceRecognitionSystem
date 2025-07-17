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

public class FaceDetector {
    private final V2Grpc.V2BlockingStub client;

    public FaceDetector() {
        Dotenv dotenv = Dotenv.load();
        String pat = dotenv.get("CLARIFAI_PAT");
        if (pat == null || pat.isEmpty()) {
            throw new IllegalStateException("CLARIFAI_PAT is not set in .env file");
        }
        client = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials(pat));
    }

    public List<BoundingBox> detectFaces(Image image) throws Exception {
        if (image == null || image.getPath() == null || image.getFaceId() == null) {
            throw new IllegalArgumentException("Image and its properties cannot be null");
        }
        byte[] imageData = Files.readAllBytes(Paths.get(getClass().getResource(image.getPath()).toURI()));
        MultiOutputResponse response = client.postModelOutputs(
                PostModelOutputsRequest.newBuilder()
                        .setModelId("face-detection")
                        .addInputs(Input.newBuilder().setData(
                                Data.newBuilder().setImage(com.clarifai.grpc.api.Image.newBuilder().setBase64(ByteString.copyFrom(imageData)))))
                        .build());
        List<BoundingBox> boundingBoxes = new ArrayList<>();
        for (Output output : response.getOutputsList()) {
            for (Region region : output.getData().getRegionsList()) {
                BoundingBox box = region.getRegionInfo().getBoundingBox();
                boundingBoxes.add(box);
                System.out.println("Face detected in " + image.getFaceId() + ": Top=" + box.getTopRow() +
                        ", Left=" + box.getLeftCol() + ", Bottom=" + box.getBottomRow() +
                        ", Right=" + box.getRightCol());
            }
        }
        return boundingBoxes;
    }
}