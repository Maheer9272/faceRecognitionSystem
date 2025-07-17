package com.maheer9272.face.util;

import com.maheer9272.face.model.Face;
import java.util.*;

public class FaceManager {
    private final List<Face> faces = new ArrayList<>();
    private final HashMap<String, Face> faceMap = new HashMap<>();

    public void addFace(Face face) {
        faces.add(face);
        faceMap.put(face.getId(), face);
    }

    public Face getFace(String id) {
        return faceMap.get(id);
    }

    public List<Face> getAllFaces() {
        return new ArrayList<>(faces);
    }

    public Face getFaceById(String id) {
        return faceMap.get(id);
    }

    public void displayFaces() {
        for (Face face : faces) {
            System.out.println(face);
        }
    }
}
