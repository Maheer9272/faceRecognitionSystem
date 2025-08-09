# Face Recognition System (Improved Demo)

## What's new
- Demo mode that works without AWS credentials.
- JavaFX Maven build and launcher.
- Clear UI even if images are missing.
- Safer DB config (no hardcoded secrets in code).

## Quick start (Demo mode)
1. Ensure JDK 17+ is installed (`java -version`).
2. Place 2–3 JPG images here:
   - `src/main/resources/images/image1.jpg`
   - `src/main/resources/images/image2.jpg`
   - `src/main/resources/images/image3.jpg`
3. Run:
   - `mvn -q -DskipTests javafx:run -Ddemo=true`

You should see a window titled "Face Recognition Results (Demo)" with red rectangles on each image.

## Using AWS Rekognition (non-demo)
- Set AWS credentials (environment, profile, or instance role).
- Run:
  - `mvn -q -DskipTests javafx:run -Ddemo=false`
- The app will call Rekognition and draw actual detections.

## Common issues
- Empty window or message "Missing: /images/imageX.jpg":
  - Make sure the images exist under `src/main/resources/images/` and are added to your build.
- JavaFX errors on startup:
  - Use the provided Maven run command. It configures JavaFX modules automatically.

## Notes
- Database credentials should be provided via environment variables or properties; avoid hardcoding secrets.
- This project runs as a desktop JavaFX application; it also logs console output for diagnostics.
