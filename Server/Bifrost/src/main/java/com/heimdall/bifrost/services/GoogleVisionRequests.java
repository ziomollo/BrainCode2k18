package com.heimdall.bifrost.services;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoogleVisionRequests {

    Logger logger = LoggerFactory.getLogger(GoogleVisionRequests.class);
    public Optional<List<String>> getTagsFromAnImage(byte[] bytes) {
        ArrayList<String> strings = new ArrayList<>();

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            ByteString imgBytes = ByteString.copyFrom(bytes);

            List<AnnotateImageRequest> requests = new ArrayList<>();

            Image img = Image.newBuilder().setContent(imgBytes).build();

            Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feature)
                    .setImage(img)
                    .build();
            requests.add(request);

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation.getAllFields().forEach((k, v) ->
                            strings.add(String.format("%s : %s\n", k, v.toString())));
                }
            }
        } catch (IOException e) {
            return Optional.empty();
        }

        return Optional.of(strings);
    }

}
