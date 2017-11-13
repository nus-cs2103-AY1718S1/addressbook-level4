package seedu.address.model.person;

//@@author Linus
import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's avatar picture in the address book.
 */
public class Avatar {
    public static final String MESSAGE_AVATAR_PIC_CONSTRAINTS =
            "Person's avatar must be a valid image URL";
    public static final String MESSAGE_AVATAR_PIC_EXPIRED =
            "This avatar is invalid, select another image URL";
    public static final String DEFAULT_URL = "http://139.59.227.237/public/default.jpg";

    /*
     * The first character of the address must not be a whitespace
     */
    public static final String AVATAR_PIC_VALIDATION_REGEX = "[^\\s].*";

    public final String source;

    public Avatar() {
        source = DEFAULT_URL;
    }

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given AvatarPic string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        requireNonNull(url);

        int isValidUrl = isValidUrl(url);

        /*
        *  Expired Image URL
        * */
        if (isValidUrl == -2) {

            throw new IllegalValueException(MESSAGE_AVATAR_PIC_EXPIRED);
        }

        /*
        * Invalid Image URL
        * */
        if (isValidUrl == -1) {

            throw new IllegalValueException(MESSAGE_AVATAR_PIC_CONSTRAINTS);
        }

        source = url;
    }

    /**
     * Returns true if a given string is a valid image URL.
     */
    public static int isValidUrl(String test) {

        if (test.matches(AVATAR_PIC_VALIDATION_REGEX)) {
            try {
                Image img = ImageIO.read(new URL(test));
                if (img == null) {
                    return -1;
                }
            } catch (IOException e) {
                if (test.compareTo(DEFAULT_URL) == 0) {
                    return 0;
                } else {
                    return -2;
                }
            }
            return 0;
        }
        return -1;
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
