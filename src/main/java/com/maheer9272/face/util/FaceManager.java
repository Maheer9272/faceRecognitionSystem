package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FaceManager {
    private static final Logger logger = LoggerFactory.getLogger(FaceManager.class);

    public List<Face> getFaces() {
        List<Face> faces = new ArrayList<>();
        faces.add(new Face("f1", "BOB", new double[]{0.1, 0.2}));
        faces.add(new Face("f2", "Alice", new double[]{0.3, 0.4}));
        faces.add(new Face("f3", "Karen", new double[]{0.5, 0.6}));
        faces.add(new Face("f4", "Donny", new double[]{0.7, 0.8}));
        faces.add(new Face("f5", "Eva", new double[]{0.9, 1.0}));
        logger.info("Retrieved {} faces", faces.size());
        return faces;
    }
}