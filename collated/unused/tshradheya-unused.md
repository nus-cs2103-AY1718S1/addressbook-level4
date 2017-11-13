# tshradheya-unused
###### \java\seedu\address\commons\events\storage\ReadAndStoreImage.java
``` java
package seedu.address.commons.events.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/**
 * To read image from specified path and store in @file resources/pictures
 */
public class ReadAndStoreImage {

    /**
     * @param path
     * @return uniquePath new path in directory
     */
    public String execute(String path, int newPath) throws IOException {

        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;

        try {
            fileToRead = new File(path);
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(fileToRead);

            uniquePath = Integer.toString(newPath);

            fileToWrite = new File("src\\main\\resources\\pictures\\" + uniquePath + ".jpg");
            ImageIO.write(image, "jpg", fileToWrite);


        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    DisplayPictureCommand.MESSAGE_IMAGE_PATH_FAIL));
        }

        return uniquePath;

    }


}
```
