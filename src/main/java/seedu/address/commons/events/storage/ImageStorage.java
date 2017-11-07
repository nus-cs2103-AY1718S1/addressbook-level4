//@@author RonakLakhotia
package seedu.address.commons.events.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/** Reads and stores image file
 */

public class ImageStorage {

    /** Reads and stores image file
     */

    public String execute(String path, int newPath) throws IOException {


        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;

        try {
            String home = System.getProperty("user.home");
            java.nio.file.Path path1 = java.nio.file.Paths.get(home, "Desktop", path);
            String url = path1 + "";
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            fileToRead = new File(url);
            image = ImageIO.read(fileToRead);
            uniquePath = Integer.toString(newPath);
            fileToWrite = new File("src/main/resources/images/" + uniquePath + ".jpg");
            ImageIO.write(image, "jpg", fileToWrite);


        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    PhotoCommand.MESSAGE_FILE_PATH_NOT_FOUND));
        }

        return uniquePath;

    }

}
