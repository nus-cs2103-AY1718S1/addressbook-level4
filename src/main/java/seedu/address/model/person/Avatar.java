package seedu.address.model.person;

//@@author Linus

import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile picture in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Avatar {
    public static final String MESSAGE_PROFILE_PIC_CONSTRAINTS =
            "Person's avatar must be a valid image URL";
    public static final String DEFAULT_URL = "https://www.gravatar.com/avatar/null";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PROFILE_PIC_VALIDATION_REGEX = "[^\\s].*";

    public final String source;

    public Avatar() {
        source = DEFAULT_URL;
    }

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given profilePic string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        requireNonNull(url);
        if (!isValidUrl(url)) {
            throw new IllegalValueException(MESSAGE_PROFILE_PIC_CONSTRAINTS);
        }

        source = url;
    }

    /**
     * Returns true if a given string is a valid image URL.
     */
    public static boolean isValidUrl(String test) {
        if (test.matches(PROFILE_PIC_VALIDATION_REGEX)) {
            try {
                Image img = ImageIO.read(new URL(test));
                if (img == null) {
                    return false;
                }
            } catch (IOException e) {
                if (test.compareTo(DEFAULT_URL) == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return source;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.source.equals(((Avatar) other).source)); // state check
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }
}
