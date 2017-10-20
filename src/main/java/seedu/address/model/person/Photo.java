package seedu.address.model.person;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;

/**
 * Represents a Person's display picture in the address book
 * Guarantees: immutable; is valid as declared in (@Link #isValidImage(String))
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Person's photo should be in jpeg and preferred to be of 340px x 453px dimension";
    public static final String MESSAGE_PHOTO_NOT_FOUND = "Error! Photo does not exist!";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PHOTO_VALIDATION_REGEX = "([^\\s]+[\\s\\w]*(\\.(?i)(jpg|jpeg|))$)";

    public final String value;

    /**
     * Validates given photo.
     *
     * @throws IllegalValueException if given photo string is invalid.
     */
    public Photo(String photo) throws IllegalValueException {
        photo = photo.trim();

        if (!isValidPhoto(photo)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        } else {
            if (!FileUtil.isFileExists(new File(photo))) {
                throw new IllegalValueException(MESSAGE_PHOTO_NOT_FOUND);
            }
        }
        this.value = photo;
    }

    /**
     * Returns true if a given string is a valid person photo.
     */
    public static boolean isValidPhoto(String test) {
        return test.matches(PHOTO_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.value.equals(((Photo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
