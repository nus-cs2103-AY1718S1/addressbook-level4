package seedu.address.model.person;

import java.io.File;

import javafx.scene.image.Image;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile picture in the address book.
 */
public class ProfilePicture {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "Profile picture file name invalid";

    public static final String DEFAULT_PICTURE = "default_pic";
    private static final String PATH_TO_IMAGE_FOLDER = "/images/";

    public final String value;

    /**
     * Validates picture given file name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ProfilePicture(String fileName) throws IllegalValueException {
        try {
            Image im = new Image(getPath(fileName));
            this.value = fileName;
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(MESSAGE_FILENAME_CONSTRAINTS);
        } catch (NullPointerException npe) {
            throw new IllegalValueException(MESSAGE_FILENAME_CONSTRAINTS);
        }
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
