package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author Melvin-leo
/**
 * Represents a Meeting's place in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Place {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Places can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Place(String address) throws IllegalValueException {
        requireNonNull(address);
        if (!isValidAddress(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.meeting.Place // instanceof handles nulls
                && this.value.equals(((seedu.address.model.meeting.Place) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
