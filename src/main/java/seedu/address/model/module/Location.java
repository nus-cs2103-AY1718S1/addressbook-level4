package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Lesson's location in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Lesson location can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given location.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        requireNonNull(location);
        if (!isValidLocation(location)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = location;
    }

    /**
     * Get a StringProperty of the location
     */
    public StringProperty getAddressProperty() {
        return new SimpleStringProperty(value);
    }

    /**
     * Returns true if a given string is a valid lesson location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
