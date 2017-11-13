package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lawwman
/**
 * Represents a Person's interest on his / her debt in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInterest(String)}
 */
public class Interest {

    public static final String NO_INTEREST_SET = "No interest set.";
    public static final String MESSAGE_INTEREST_CONSTRAINTS =
            "Interest can only contain numbers, and should have 1 or more digits";
    public static final String INTEREST_VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Validates given interest.
     *
     * @throws IllegalValueException if given interest string is invalid.
     */
    public Interest(String interest) throws IllegalValueException {
        requireNonNull(interest);
        String trimmedInterest = interest.trim();
        if (trimmedInterest.equals(NO_INTEREST_SET)) {
            this.value = trimmedInterest;
        } else {
            if (!isValidInterest(trimmedInterest)) {
                throw new IllegalValueException(MESSAGE_INTEREST_CONSTRAINTS);
            }
            this.value = trimmedInterest;
        }
    }

    /**
     * Returns true if a given string is a valid person interest.
     */
    public static boolean isValidInterest(String test) {
        return test.matches(INTEREST_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interest // instanceof handles nulls
                && this.value.equals(((Interest) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
