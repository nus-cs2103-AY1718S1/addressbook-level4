//@@author wishingmaid
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import seedu.address.commons.util.ExtensionCheckerUtil;

/**
 * Represents the conversion of a local filepath inside the user's hard drive
 * into a new filepath that is local to the jar file of PEERSONAL.
 * Guarantees : The newly written filepath must exist if ImageIO.write is successful
 */
public class PhotoStorage {
    public static final String WRITE_FAILURE_MESSAGE = "Unable to write to local resource folder: displaypictures.\n"
            + "Make sure that the image type is supported and the image file cannot be hidden.\n"
            + "Supported types: JPEG, JPG, PNG.";
    private String[] allowedExt = null;
    private File fileReader = null;
    private String filePath = "";
    private File fileWriter = null;
    private BufferedImage imageReader = null;
    private String uniqueFileName;
    public PhotoStorage(String filePath) {
        this.filePath = filePath;
        imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
        allowedExt =  new String[]{"jpg", "png", "JPEG"};
    }

    public String setNewFilePath() throws IOException {
        requireNonNull(allowedExt);
        String ext = ExtensionCheckerUtil.getExtension(filePath);
        if (!ExtensionCheckerUtil.isOfType(ext, allowedExt)) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        }
        uniqueFileName = generateUniqueFileName();
        requireNonNull(uniqueFileName);
        String newFilePath = "displaypictures/" + uniqueFileName + "." + ext;
        try {
            fileReader = new File(filePath);
            imageReader = ImageIO.read(fileReader);
            fileWriter = new File(newFilePath);
            ImageIO.write(imageReader, ext, fileWriter);
            return newFilePath;
        } catch (IOException e) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        }
    }

    /** generates a unique file path that is to be saved into displaypictures */
    private String generateUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        String uniqueFilePath = uuid.toString();
        return uniqueFilePath;
    }
}
