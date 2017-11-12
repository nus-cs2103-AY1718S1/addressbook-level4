package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import java.io.File;

//@@author LuLechuan
/**
 * Represents a Person's photo in the address book.
 */
public class Photo {

    public static final String MESSAGE_PHOTO_NOT_FOUND = "The given path name does not exist";

    public final String pathName;

    /**
     *  Constructs a default photo.
     */
    public Photo() {
        pathName = System.getProperty("user.dir")
                + "/docs/images/default_photo.png";
    }

    /**
     * Constructs with a given pathName.
     */
    public Photo(String pathName) throws IllegalValueException {
        if (isUnknownPath(pathName)) {
            throw new IllegalValueException(MESSAGE_PHOTO_NOT_FOUND);
        }

        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }

    /**
     *
     * @return true if a given pathname has unknown value
     */
    public static boolean isUnknownPath(String test) {
        File file = new File(test);
        return !file.exists();
    }

    @Override
    public String toString() {
        return pathName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.pathName.equals(((Photo) other).pathName)); // state check
    }

    @Override
    public int hashCode() {
        return pathName.hashCode();
    }

}
