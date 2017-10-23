package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a file path to Person's saved Avatar
 * Guarantees: immutable; is valid as declared in {@link #isValidFilePath(String)}
 */
public class Avatar {


    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar file path must be .jpg or .png and must exist";
    private static final String AVATAR_FILE_PATH = "src\\main\\resources\\pictures\\";
    private String value;

    public Avatar(String filePath, String name) throws IllegalValueException {
        requireNonNull(filePath);
        String isTrimmedFilePath = filePath.trim();
        if (!isValidFilePath(isTrimmedFilePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        String formattedName = formatName(name);
        if (saveFile(isTrimmedFilePath, formattedName)) {
            this.value = AVATAR_FILE_PATH + formattedName + getFormat(filePath);
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    //assumes that filepath is either jpg or png.
    private boolean saveFile(String filePath, String name) {
        String format = "png";

        if (isJpg(filePath)) {
            format = "jpg;";
        }

        try {
            File imageFile = new File(filePath);
            BufferedImage image = ImageIO.read(imageFile);
            ImageIO.write(image, format, new File(AVATAR_FILE_PATH + name + "." + format));

        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    private String formatName(String name) {
        String formatted = name.trim();
        formatted = formatted.replace(" ", "_");
        return formatted;
    }


    private boolean isJpg(String filePath) {
        return filePath.toLowerCase().endsWith(".jpg");
    }

    private boolean isPng(String filePath) {
        return filePath.toLowerCase().endsWith(".png");
    }

    private boolean isJpgOrPng(String filePath) {
        return isJpg(filePath) || isPng(filePath);
    }

    private String getFormat(String filePath) {
        if(isJpg(filePath)) {
            return ".jpg";
        }

        return ".png";
    }


    private boolean isValidFilePath(String filePath) {

        if (!isJpgOrPng(filePath)) {
            return false;
        }

        File f = new File(filePath);
        if(!f.exists() || f.isDirectory()) {
            return false;
        }

        return true;
    }
}
