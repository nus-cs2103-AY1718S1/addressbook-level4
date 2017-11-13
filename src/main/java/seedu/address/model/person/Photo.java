//@@author viviantan95
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a photo of a Person in the addressbook.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhoto(String)}
 */
public class Photo {
    public static final String MESSAGE_PHOTO_CONSTRAINTS =
            "Person's photo must have a valid image URL.";

    public static final String PHOTO_VALIDATION_REGEX = "[^\\s].*";

    private static final String DEFAULT_PHOTO = "images/defaultPhoto.png";

    public final String value;
    private Image image;

    /**
     * Validates given photo
     *
     * @throws IllegalValueException if given photo url is invalid.
     */
    public Photo() {
        value = DEFAULT_PHOTO;
    }

    public Photo(String photo) throws IllegalValueException {
        requireNonNull(photo);
        if (!isValidPhoto(photo)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.value = photo;
    }

    //@@author jshoung
    public String getBrowserPhoto() {
        return value;
    }
    //@@author

    //@@author viviantan95
    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidPhoto(String test) {
        if (test.equals("images/defaultPhoto.png")) {
            return true;
        }

        if (test.matches(PHOTO_VALIDATION_REGEX)) {
            try {
                Image image = ImageIO.read(new URL(test));
                if (image == null) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
            return true;

        }
        return false;
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
