package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/mm/yy hh:mm duration, the date must be older than today";

    /*
     * Placeholder, will move to use other method instead of regex checking
     */
    public static final String APPOINTMENT_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given appointment.
     *
     * @throws IllegalValueException if given appointment string is invalid.
     */
    public Appointment(String appointment) throws IllegalValueException {
        requireNonNull(appointment);
        if (!isValidAddress(appointment)) {
            throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
        }
        this.value = appointment;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(APPOINTMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
