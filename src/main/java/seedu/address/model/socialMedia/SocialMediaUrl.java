package seedu.address.model.socialMedia;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Schedule's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class SocialMediaUrl {

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Schedule names should contain only "
            + "alphanumeric characters, spaces, underscores and dashes";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String SCHEDULE_VALIDATION_REGEX =
            "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public final String url;

    /**
     * Validates given url.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public SocialMediaUrl(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidUrl(trimmedName)) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_CONSTRAINTS);
        }
        this.url = trimmedName;
    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidUrl(String test) {
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialMediaUrl // instanceof handles nulls
                && this.url.equals(((SocialMediaUrl) other).url)); // state check
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

}
