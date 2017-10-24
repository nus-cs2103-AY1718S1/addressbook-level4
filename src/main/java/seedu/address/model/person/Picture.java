package seedu.address.model.person;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's picture's name
 * Guarantees: immutable; is always valid
 */
public class Picture {

    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "There should be a valid location to the picture and the picture must be a .png";
    public static final String PICTURE_IMAGE_LOCATION = "images/"; // Where images are stored when added

    private static final String PICTURE_SUFFIX = ".png";
    private static final String PICTURE_DELIMITER = ".png";

    public final String value;


    public Picture(String fileLocation) throws IllegalValueException {
        String trimmedFileLocation = fileLocation == null ? null : fileLocation.trim();
        if (!isValidPicture(trimmedFileLocation)) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        }

        String [] split = trimmedFileLocation.split(PICTURE_DELIMITER);

        // last value before / is picture we want
        this.value = split[split.length - 1];
    }

    /**
     * Checks if the picture exists and ends with a .png
     * @param fileLocation location of file
     * @return true if picture is valid
     */
    public boolean isValidPicture(String fileLocation) {
        File file = new File(fileLocation);
        if (file.exists() && fileLocation.endsWith(PICTURE_SUFFIX)) {
            return true;
        } else {
            return false;
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
