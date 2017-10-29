package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the conversion of a local filepath inside the user's hard drive
 * into a new filepath that is local to the jar file of PEERSONAL.
 * Guarantees : The newly written filepath must exist if ImageIO.write is successful
 */
public class PhotoStorage {
    public static final String WRITE_FAILURE_MESSAGE = "Unable to write to local resource folder: displaypictures. " +
            "Make sure that the image type is supported. Supported types: JPEG, PNG, GIF.";
    private File fileReader = null;
    private String filePath = "";
    private File fileWriter = null;
    BufferedImage imageReader = null;
    private int uniqueFileName;
    public PhotoStorage(String filePath, int uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
        this.filePath = filePath;
        imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
    }

    public String setNewFilePath() throws IOException {
        String newFilePath = "displaypictures/" + uniqueFileName + ".jpg";
        try {
            fileReader = new File(filePath);
            imageReader = ImageIO.read(fileReader);
            fileWriter = new File(newFilePath);
            ImageIO.write(imageReader, "jpg", fileWriter);
            return newFilePath;
        } catch (IOException e ) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        } catch (IllegalArgumentException f) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        }
    }
}
