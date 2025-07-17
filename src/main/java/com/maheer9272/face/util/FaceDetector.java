package com.maheer9272.face.util;



import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.protobuf.ByteString;
import com.maheer9272.face.model.Image;

public class FaceDetector {
    private final V2Grpc.V2BlockingStub client;

    public FaceDetector() {
        Dotenv dotenv = Dotenv.load();
        String pat = dotenv.get("CLARIFAI_PAT");
        client = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials(pat));
    }

    public void detectFaces(Image image) throws Exception {
        byte[] imageData = Files.readAllBytes(Paths.get(image.getPath()));
        MultiOutputResponse response = client.postModelOutputs(
                PostModelOutputsRequest.newBuilder()
                        .setModelId("face-detection")
                        .addInputs(Input.newBuilder().setData(
                                Data.newBuilder().setImage(com.clarifai.grpc.api.Image.newBuilder().setBase64(ByteString.copyFrom(imageData)))))
                        .build());
        for (Output output : response.getOutputsList()) {
            for (Region region : output.getData().getRegionsList()) {
                BoundingBox box = region.getRegionInfo().getBoundingBox();
                System.out.println("Face detected in " + image.getFaceId() + ": Top=" + box.getTopRow() +
                        ", Left=" + box.getLeftCol() + ", Bottom=" + box.getBottomRow() +
                        ", Right=" + box.getRightCol());
            }
        }
    }
}
