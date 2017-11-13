package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kaiyu92

/**
 * Represents a Event's Location in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Event location should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\w].*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!isValidLocation(trimmedLocation)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = trimmedLocation;
    }

    /**
     * Returns true if a given string is a valid event location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
