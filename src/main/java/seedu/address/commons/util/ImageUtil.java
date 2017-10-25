package seedu.address.commons.util;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * Handles the IO for editing persons images
 */
public class ImageUtil {

    /**
     * Reads the image using the provided path if correct and stores it locally
     * @param path of the file
     * @return uniquePath of the new local image
     * @throws IOException if the path is missing
     */
    public String run(String path, int newPath) throws IOException {
        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;

        try {
            fileToRead = new File(path);
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            fileToWrite = new File("pictures/" + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);

        } catch (IOException ioe) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    ImageCommand.MESSAGE_IMAGE_PATH_FAIL));
        }

        return uniquePath;
    }

}
