//@@author tshradheya
package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * To read and store image
 */
public class ImageDisplayPictureStorage implements DisplayPictureStorage {

    private static final int IMAGE_WIDTH = 980;
    private static final int IMAGE_HEIGHT = 640;

    private static final String IMAGE_EXTENSION = ".png";
    private static final String DIRECTORY_SAVING_PATH = "pictures/";

    private static final Logger logger = LogsCenter.getLogger(ImageDisplayPictureStorage.class);


    public ImageDisplayPictureStorage() {
        logger.info("Constructor used to create instance of DisplayPictureStorage.class");
    }

    /**
     * Reads image from local device
     * @throws IOException to display imagePath is wrong
     */
    public void readImageFromDevice(String imagePath, int newPath) throws IOException {
        File fileToRead = null;
        BufferedImage image = null;
        String uniquePath = null;

        try {
            logger.info(" Image read from path " + imagePath);
            fileToRead = new File(imagePath);
            image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            saveImageInDirectory(image, uniquePath);
        } catch (IOException ioe) {
            logger.info("Image not read properly");
            throw new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));

        }
    }

    /**
     * To store image in directory
     * @throws IOException to display error message
     */
    public void saveImageInDirectory(BufferedImage image, String uniquePath) throws IOException {
        File fileToWrite = null;
        try {
            logger.info("image is being stored in directory ");
            fileToWrite = new File(DIRECTORY_SAVING_PATH + uniquePath + IMAGE_EXTENSION);
            ImageIO.write(image, IMAGE_EXTENSION, fileToWrite);
        } catch (IOException ioe) {
            logger.info("Image not saved properly");
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));
        }
    }

}
