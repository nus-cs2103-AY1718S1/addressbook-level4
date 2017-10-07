package seedu.address.model.event.exceptions;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's Description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {
    public static final String MESSAGE_Description_CONSTRAINTS =
            "Event Descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String Description;

    /**
     * Validates given Description.
     *
     * @throws IllegalValueException if given Description string is invalid.
     */
    public Description(String Description) throws IllegalValueException {
        requireNonNull(Description);
        String trimmedDescription = Description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_Description_CONSTRAINTS);
        }
        this.Description = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid event's Description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return Description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.Description.equals(((Description) other).Description)); // state check
    }

    @Override
    public int hashCode() {
        return Description.hashCode();
    }
}
