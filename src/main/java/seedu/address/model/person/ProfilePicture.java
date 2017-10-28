package seedu.address.model.person;

import java.io.File;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile picture in the address book.
 */
public class ProfilePicture {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "Profile picture file name invalid";

    public static final String DEFAULT_PICTURE = "default_profile_picture.png";
    private static final String PATH_TO_IMAGE_FOLDER = "/images/";

    public final String value;

    /**
     * Validates picture given file name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ProfilePicture(String fileName) throws IllegalValueException {
        try {
            if (!(fileName.contains("/") || fileName.contains("\\"))) {
                this.value = PATH_TO_IMAGE_FOLDER + fileName;
            } else {
                File file = new File(fileName);
                this.value = file.toURI().toString();
            }
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_FILENAME_CONSTRAINTS);
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
