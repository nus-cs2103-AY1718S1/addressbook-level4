package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/mm/yy hh:mm duration, the date must be older than today";

    public final String value;

    /**
     * Validates given appointment.
    */
    public Appointment(String appointment) {
        requireNonNull(appointment);
        this.value = appointment;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.value.equals(((Appointment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
