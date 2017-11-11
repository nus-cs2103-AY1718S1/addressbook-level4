package seedu.address.commons.util;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.ImageException;
import seedu.address.logic.commands.ImageCommand;

//@@author aali195
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
    public String run(String path, int newPath) throws IOException, ImageException {
        String imageDirectory = "data/";
        checkDirectory(imageDirectory);

        File fileToRead;
        BufferedImage image;

        File fileToWrite;

        String uniquePath;

        try {
            fileToRead = new File(path);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            fileToWrite = new File(imageDirectory + uniquePath + ".png");
            ImageIO.write(image, "png", fileToWrite);

        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE, ImageCommand.MESSAGE_IMAGE_PATH_FAIL));
        }

        return uniquePath;
    }

    /**
     * Creates a directory if it does not exist
     * @param imageDirectory to be checked and created
     */
    private void checkDirectory(String imageDirectory) throws IOException {
        Path path = Paths.get(imageDirectory);
        if (!Files.exists(path)) {
            Files.createDirectories(Paths.get(imageDirectory));
        }
    }

}
//@@author
