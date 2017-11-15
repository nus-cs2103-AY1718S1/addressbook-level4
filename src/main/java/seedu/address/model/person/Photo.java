package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's photo in the address book.
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Please ensure you enter the correct file path (either absolute "
                    + "or relative). Photos can be in either jpg, jpeg or png format.";

    //PhotoUrl can either start with a '/' or '\' (for absolute path), or '.'
    // or English characters (for relative path)
    public static final String PHOTOURL_VALIDATION_REGEX =
            "[\\.\\\\\\/\\w].*\\.(jpg|png|jpeg)";

    public final String photoUrl;

    /**
     * Validates given photo URL.
     *
     * @throws IllegalValueException if given photo URL string is invalid.
     */
    public Photo(String photoUrl) throws IllegalValueException {
        requireNonNull(photoUrl);
        String photoUrlTrimmed = photoUrl.trim();
        if (!isValidPhotoUrl(photoUrlTrimmed)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.photoUrl = photoUrlTrimmed;
    }

    /**
     * Returns true if a given string is a valid person photo.
     */
    public static boolean isValidPhotoUrl(String testUrl) {
        return testUrl.matches(PHOTOURL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.photoUrl.equals(((Photo) other).photoUrl));
    }

    @Override
    public int hashCode() {
        return photoUrl.hashCode();
    }

}
