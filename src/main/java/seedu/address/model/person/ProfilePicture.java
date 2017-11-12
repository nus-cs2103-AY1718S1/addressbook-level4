//@@author arturs68
package seedu.address.model.person;

import java.io.File;

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
    public ProfilePicture(String fileName) {
        this.value = fileName;
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
