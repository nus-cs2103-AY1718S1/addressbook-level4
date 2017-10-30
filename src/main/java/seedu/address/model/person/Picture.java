//@@author Jemereny
package seedu.address.model.person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserPrefs;

/**
 * Represents a Person's picture's name
 * Guarantees: immutable; is always valid
 */
public class Picture {

    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "There should be a valid location to the picture, the picture must be a .png "
                    + "and the size is 512KB or less";
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

    private static final String PICTURE_SUFFIX = ".png";
    private static final String PICTURE_DELIMITER = "/";
    private static final int PICTURE_MAX_SIZE = 512000; // 512 KB

    public final String value;

    public Picture(String fileLocation) throws IllegalValueException {
        String trimmedFileLocation = fileLocation == null ? null : fileLocation.trim();
        if (!isValidPicture(trimmedFileLocation)) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        }

        if (trimmedFileLocation != null) {
            String[] split = trimmedFileLocation.split(PICTURE_DELIMITER);

            // When we save the file, it is a single file name there is nothing to split.
            // No need to copy it either

            // last value before '/' is picture we want
            this.value = split[split.length - 1];

            // length will give 1 when it is the file we saved
            // in that case just put PICTURE_IMAGE_LOCATION to find it
            if (split.length != 1) {
                File src = new File(fileLocation);
                File dest = new File(PICTURE_SAVE_LOCATION + this.value);

                try {
                    FileUtils.copyFile(src, dest);
                } catch (IOException e) {
                    throw new IllegalValueException(MESSAGE_PROFILEPICTURE_ERROR);
                }
            }
        } else {
            this.value = null;
        }

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

        // For default people
        if (fileLocation == Picture.DEFAULT_ALEX || fileLocation == Picture.DEFAULT_BALAKRISHNAN
                || fileLocation == Picture.DEFAULT_BERNICE || fileLocation == Picture.DEFAULT_CHARLOTTE
                || fileLocation == Picture.DEFAULT_DAVID || fileLocation == Picture.DEFAULT_IRFAN) {
            return true;
        }

        File file = new File(fileLocation);
        if (file.exists() && file.length() <= PICTURE_MAX_SIZE && fileLocation.endsWith(PICTURE_SUFFIX)) {
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
     * Returns default picture location if there is no value
     */
    public String getPictureLocation() {
        if (value == null) {
            return DEFAULT_PICTURE_LOCATION + DEFAULT_PICTURE;
        } else if (value == Picture.DEFAULT_ALEX || value == Picture.DEFAULT_BALAKRISHNAN
                || value == Picture.DEFAULT_BERNICE || value == Picture.DEFAULT_CHARLOTTE
                || value == Picture.DEFAULT_DAVID || value == Picture.DEFAULT_IRFAN) {
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
