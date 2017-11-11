package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author LuLechuan
/**
 * Represents a Person's photo in the address book.
 */
public class Photo {

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
        //requireNonNull(pathName);

        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
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
