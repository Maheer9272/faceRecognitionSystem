package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import java.util.*;

public class FaceManager {
    private final List<Face> faces = new ArrayList<>();
    private final HashMap<String, Face> faceMap = new HashMap<>();

    public void addFace(Face face) {
        if (face == null || face.getId() == null || face.getId().isEmpty()) {
            throw new IllegalArgumentException("Face or its ID cannot be null or empty");
        }
        faces.add(face);
        faceMap.put(face.getId(), face);
    }

    public Face getFace(String id) {
        return faceMap.get(id);
    }

    public List<Face> getAllFaces() {
        return Collections.unmodifiableList(new ArrayList<>(faces)); // Return unmodifiable list
    }

    public Face getFaceById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return faceMap.get(id);
    }

    public void displayFaces() {
        for (Face face : faces) {
            System.out.println(face);
        }
    }
}
