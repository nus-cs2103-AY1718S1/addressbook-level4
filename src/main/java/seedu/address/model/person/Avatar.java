package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.storage.util.ProcessImageUtil;

//@@author karrui
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class Avatar {


    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image not found/ image extension not supported! Only supports \"BMP\", \"GIF\", \"JPEG\", and \"PNG\"\n"
            + "Image must also be 50KB or smaller";
    public static final String MESSAGE_IMAGESIZE_CONSTRAINTS = "Image is too big! Please keep size to 50KB or lower";
    public static final String DEFAULT_AVATAR_FILE_LOCATION = "./data/avatar/";
    public final String path;
    public final String initialUrl;

    /**
     * Validates given avatar.
     * Invokes ProcessImageUtil for processing of storage of avatar
     * @throws IllegalValueException if given path string is invalid.
     */
    public Avatar(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();
        if (!isValidPath(trimmedPath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        this.initialUrl = trimmedPath;
        this.path = ProcessImageUtil.writeImageToStorage(trimmedPath);
    }

    /**
     * Returns true if a given string is a valid image filepath
     */
    public static boolean isValidPath(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        return isImageValid(path) && isImageCorrectSize(path);
    }

    /**
     * Returns true if image can be loaded from path
     */
    public static boolean isImageValid(String path) {
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            return image != null;
        } catch (IOException ioe) {
            // invalid URL, check if is file path
            try {
                BufferedImage image = ImageIO.read(new File(path));
                return image != null;
            } catch (IOException ioe2) {
                return false;
            }
        }
    }

    /**
     * Returns true if image is smaller than 50KB.
     * (This is because if the image is too big, the application will start slowing down)
     */
    public static boolean isImageCorrectSize(String path) {
        if ("".equals(path)) {  // default
            return true;
        }
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            // invalid URL, or is file path, check file size instead
            return ((new File(path).length()) / 1024) < 50;
        }
        int fileSize = getFileSize(url) / 1024;     // file size in KBs
        return fileSize < 50 && fileSize > 0;
    }

    /**
     * Obtains file size of image using a HEAD http request
     */
    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            assert conn != null;
            conn.disconnect();
        }
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.initialUrl.equals(((Avatar) other).initialUrl)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
