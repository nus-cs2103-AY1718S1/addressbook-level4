package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's instagram in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstagram(String)}
 */
//@@author bokwoon95
public class Instagram implements Comparable {

    public static final String MESSAGE_INSTAGRAM_CONSTRAINTS =
            "Person addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INSTAGRAM_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Instagram(String instagram) throws IllegalValueException {
        requireNonNull(instagram);
        if (!isValidInstagram(instagram)) {
            throw new IllegalValueException(MESSAGE_INSTAGRAM_CONSTRAINTS);
        }
        this.value = instagram;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidInstagram(String test) {
        return test.matches(INSTAGRAM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        Instagram comparedInstagram = (Instagram) o;
        return this.value.compareTo(comparedInstagram.toString());
    }

}
