//@@author arturs68
package seedu.address.model.person;

import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile picture in the address book.
 */
public class ProfilePicture {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "Profile picture file name invalid";

    public static final String DEFAULT_PICTURE = "default_pic.png";
    private static final String PATH_TO_IMAGE_FOLDER = "/images/";

    public final String value;

    /**
     * Assigns the path to the profile picture
     */
    public ProfilePicture(String fileName) throws IllegalValueException {
        String nameToBeSet = fileName;
        if (fileName.contains("/") || fileName.contains("\\")) {
            File file = new File(fileName);
            if (!file.exists()) {
                nameToBeSet = DEFAULT_PICTURE;
            }
        } else {
            if (!(fileName.equals(DEFAULT_PICTURE) || fileName.equals(SAMPLE_PICTURE))) {
                throw new IllegalValueException(
                        "Wrong file path specified. Perhaps you meant: " + DEFAULT_PICTURE + " ?");
            }
        }
        this.value = nameToBeSet;
    }

    public static String getPath(String value) {
        if (value.contains("/") || value.contains("\\")) {
            File file = new File(value);
            return file.toURI().toString();
        } else {
            return PATH_TO_IMAGE_FOLDER + value;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.value.equals(((ProfilePicture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
