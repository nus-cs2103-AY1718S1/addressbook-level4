//@@author Jemereny
package seedu.address.model.person;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserPrefs;

/**
 * Represents a Person's picture's name
 * Guarantees: immutable; is always valid
 */
public class Picture {

    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "There should be a valid location to the picture, the picture must be a .png ";
    public static final String MESSAGE_PROFILEPICTURE_ERROR =
            "Error copying file.";

    public static final String PREFIX_PICTURE = "file://";
    public static final String PICTURE_SAVE_LOCATION =
            UserPrefs.FOLDER_LOCATION; // Where images are stored when added
    public static final String DEFAULT_PICTURE_LOCATION =
            "/images/";
    public static final String DEFAULT_PICTURE =
            "default_profile.png";
    public static final String DEFAULT_ALEX =
            "default_alex.png";
    public static final String DEFAULT_BALAKRISHNAN =
            "default_balakrishnan.png";
    public static final String DEFAULT_BERNICE =
            "default_bernice.png";
    public static final String DEFAULT_CHARLOTTE =
            "default_charlotte.png";
    public static final String DEFAULT_DAVID =
            "default_david.png";
    public static final String DEFAULT_IRFAN =
            "default_irfan.png";

    private static final int RESIZE_IMAGE_WIDTH = 256;
    private static final int RESIZE_IMAGE_HEIGHT = 256;

    private static final String PICTURE_SUFFIX = ".png";
    private static final String PICTURE_DELIMITER_SLASH = "/";
    private static final String PICTURE_DELIMITER_BACKSLASH = "\\\\";
    private static final int PICTURE_MAX_SIZE = 512000; // 512 KB

    public final String value;

    public Picture(String fileLocation) throws IllegalValueException {
        String trimmedFileLocation = fileLocation == null ? null : fileLocation.trim();
        if (!isValidPicture(trimmedFileLocation)) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        }

        if (trimmedFileLocation != null) {
            String[] split = splitFileLocation(trimmedFileLocation);

            // length will give 1 when it is the file we saved
            // in that case just put PICTURE_IMAGE_LOCATION to find it
            if (split.length != 1) {
                // Rename and copied files to avoid clashing
                String newFileName = UUID.randomUUID().toString() + PICTURE_SUFFIX;

                File src = new File(trimmedFileLocation);
                File dest = new File(PICTURE_SAVE_LOCATION + newFileName);

                // If file is too big, resize it.
                if (src.length() > PICTURE_MAX_SIZE) {
                    resizeAndSaveImage(src, newFileName);
                } else {
                    copyImage(src, dest);
                }
                this.value = newFileName;
            } else {
                // Last value is file name
                this.value = split[split.length - 1];
            }
        } else {
            this.value = null;
        }
    }

    /**
     * Splits the file depending on the delimiter used '/' or '\'
     * @param trimmedFileLocation location of valid file
     * @return split fileLocation
     */
    public static String[] splitFileLocation(String trimmedFileLocation) {
        String [] split = trimmedFileLocation.split(PICTURE_DELIMITER_SLASH);

        // If the fileLocation has been split but has length of 1,
        // It is either using another delimiter or is the file itself.
        if (split.length < 2) {
            split = trimmedFileLocation.split(PICTURE_DELIMITER_BACKSLASH);
        }

        return split;
    }

    /**
     * Returns true if file location of picture is valid and the picture exist
     * @param fileLocation
     * @return
     */
    public static boolean isValidPicture(String fileLocation) {
        if (fileLocation == null) {
            return true;
        }

        if ("".equals(fileLocation)) {
            return false;
        }

        // For default people
        if (fileLocation.equals(Picture.DEFAULT_ALEX) || fileLocation.equals(Picture.DEFAULT_BALAKRISHNAN)
                || fileLocation.equals(Picture.DEFAULT_BERNICE) || fileLocation.equals(Picture.DEFAULT_CHARLOTTE)
                || fileLocation.equals(Picture.DEFAULT_DAVID) || fileLocation.equals(Picture.DEFAULT_IRFAN)) {
            return true;
        }

        File file = new File(fileLocation);
        if (file.exists() && (fileLocation.endsWith(PICTURE_SUFFIX))) {
            return true;
        } else {
            file = new File(PICTURE_SAVE_LOCATION + fileLocation);
            if (file.exists()) {
                return true;
            }

            return false;
        }
    }

    /**
     * Copies the image and puts into data folder
     * @param src Source of file to save
     * @param dest Destination of the file to save
     * @throws IllegalValueException when src or dest is an invalid file/location
     */
    public static void copyImage(File src, File dest) throws IllegalValueException {
        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_ERROR);
        }
    }

    /**
     * Resizes and saves image to data folder
     * @param file the image
     * @param newFileName file name to save as
     * @throws IllegalValueException if there is an error loading the file
     */
    public static void resizeAndSaveImage(File file, String newFileName) throws IllegalValueException {
        try {
            BufferedImage resizedImage = resizeImage(ImageIO.read(file));

            // Saving of image into data folder
            ImageIO.write(resizedImage, "png", new File(PICTURE_SAVE_LOCATION + newFileName));
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_ERROR);
        }
    }

    /**
     * Redraws the original image into a smaller canvas, resizing the image.
     * @param originalImage The original image to be resized
     * @return BufferedImage image that is redrawn and resized
     */
    public static BufferedImage resizeImage(BufferedImage originalImage) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, null);
        g.dispose();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    /**
     * Returns default picture location if there is no value
     */
    public String getPictureLocation() {
        if (value == null) {
            return DEFAULT_PICTURE_LOCATION + DEFAULT_PICTURE;
        } else if (value.equals(Picture.DEFAULT_ALEX) || value.equals(Picture.DEFAULT_BALAKRISHNAN)
                || value.equals(Picture.DEFAULT_BERNICE) || value.equals(Picture.DEFAULT_CHARLOTTE)
                || value.equals(Picture.DEFAULT_DAVID) || value.equals(Picture.DEFAULT_IRFAN)) {
            // Sample data
            return DEFAULT_PICTURE_LOCATION + value;
        } else {
            return PREFIX_PICTURE + Paths.get(PICTURE_SAVE_LOCATION + value)
                    .toAbsolutePath().toUri().getPath();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Picture)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Picture) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Picture) other).value)) { // state check
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
