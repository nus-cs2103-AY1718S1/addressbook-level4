package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.*;
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
    private static final String AVATAR_SAVE_LOCATION = "src/main/resources/images/avatars/";
    private static final String AVATAR_RESOURCE_LOCATION = "/images/avatars/";
    public static final String AVATAR_DEFAULT_LOCATION = AVATAR_RESOURCE_LOCATION + "default.png";
    public String value;
    public BufferedImage loadedImage = null;

    public Avatar(String filePath) {
        if (filePath == null) {
            this.value = AVATAR_DEFAULT_LOCATION;
            return;
        }
        this.value = filePath;
    }

    public Avatar(String filePath, String name) throws IllegalValueException {

        if (filePath == null) {
            this.value = AVATAR_DEFAULT_LOCATION;
            return;
        }

        String isTrimmedFilePath = filePath.trim();
        if (!isValidFilePath(isTrimmedFilePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        String formattedName = formatName(name);
        if (saveFile(isTrimmedFilePath, formattedName)) {
            this.value = AVATAR_RESOURCE_LOCATION + formattedName + getFormat(filePath);
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    //assumes that filepath is either jpg or png.
    private boolean saveFile(String filePath, String name) {
        String format = "png";

        if (isJpg(filePath)) {
            format = "jpg";
        }

        try {
            File imageFile = new File(filePath);
            BufferedImage image = ImageIO.read(imageFile);
            loadedImage = image;
            ImageIO.write(image, format, new File(AVATAR_SAVE_LOCATION + name + "." + format));
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
