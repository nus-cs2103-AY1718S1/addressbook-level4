//@@author tshradheya
package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a storage for Display Picture.
 */
public interface DisplayPictureStorage {

    void readImageFromDevice(String imagePath, int newPath) throws IOException;

    void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException;

}
