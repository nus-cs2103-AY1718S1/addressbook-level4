package seedu.address.model.photo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.isInFolder;
import static seedu.address.commons.util.FileUtil.isValidImageFile;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author April0616
/**
 * Represents the path of a person's photo in the address book.
 */
public class PhotoPath {

    public static final String FILE_SAVED_PARENT_PATH = "src/main/resources/images/contactPhotos/";
    public static final String MESSAGE_APP_PHOTOPATH_CONSTRAINTS =
            "The app photo path should be a string starting with '"
                    + FILE_SAVED_PARENT_PATH
                    + "', following by the file name with a valid extension, like'photo.jpg'.\n"
                    + "The valid extensions are 'jpg', 'jpeg', 'png', 'gif' or 'bmp'.";

    public final String value;

    /**
     * Initializes the photo path object and validates the given photo path string.
     * @param photoPath string of the specified photo path
     * @throws IllegalValueException if the given string is invalid
     */
    public PhotoPath(String photoPath) throws IllegalValueException {
        requireNonNull(photoPath);

        if (!isValidPhotoPath(photoPath)) {
            throw new IllegalValueException(MESSAGE_APP_PHOTOPATH_CONSTRAINTS);
        }
        this.value = photoPath;
    }

    /**
     * Returns if a given string is a valid photo path.
     */
    public static boolean isValidPhotoPath(String photoPath) {
        if (photoPath.equals("")) {
            //empty photo path
            return true;
        }
        Boolean isValidImage = isValidImageFile(photoPath);
        Boolean isInDefaultFolder = isInFolder(photoPath, FILE_SAVED_PARENT_PATH);

        return  isInDefaultFolder && isValidImage;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoPath // instanceof handles nulls
                && this.value.equals(((PhotoPath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
