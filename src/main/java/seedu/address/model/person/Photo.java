package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.net.URL;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's photo in the address book.
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Please enter the correct file path. Photos can only be in JPG " +
                    "format, and cannot contain empty spaces in the filename.";
    public static final String PHOTOURL_VALIDATION_REGEX =
            "[\\w\\/\\-\\_\\.\\h]+\\.jpg";
    public final String photoURL;

    /**
     * Validates given photo URL.
     *
     * @throws IllegalValueException if given photo URL string is invalid.
     */
    public Photo(String photoURL) throws IllegalValueException {
        requireNonNull(photoURL);
        String photoURLTrimmed = photoURL.trim();
        if (!isValidPhotoURL(photoURLTrimmed)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.photoURL = photoURLTrimmed;
    }

    /**
     * Returns true if a given string is a valid person photo.
     */
    public static boolean isValidPhotoURL(String testURL) {
        return testURL.matches(PHOTOURL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return photoURL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.photoURL.equals(((Photo) other).photoURL)); // state check
    }

    @Override
    public int hashCode() {
        return photoURL.hashCode();
    }

}
