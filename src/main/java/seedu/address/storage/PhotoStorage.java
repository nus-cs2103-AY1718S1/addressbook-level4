package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

/**
 * Represents the conversion of a local filepath inside the user's hard drive
 * into a new filepath that is local to the jar file of PEERSONAL.
 * Guarantees : The newly written filepath must exist if ImageIO.write is successful
 */
public class PhotoStorage {

    private File fileReader = null;
    private String filePath = "";
    private BufferedImage imageReader = null;
    private File fileWriter = null;
    private int uniqueFileName;

    public PhotoStorage(String filePath, int uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
        this.filePath = filePath;
        imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
    }

    public String setNewFilePath() throws IOException {
        String newFilePath= "src\\main\\resources\\displaypictures\\" + uniqueFileName + ".jpg";
        try {
            fileReader = new File(filePath);
            fileWriter = new File(newFilePath);
            imageReader = ImageIO.read(fileReader);
            ImageIO.write(imageReader, "jpg", fileWriter);
            return newFilePath;
        } catch(IOException e) {
            throw new IOException("unable to write to local resource folder");
        }
    }
}
