package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Name;

/**
 * Represents a Task's appointment in the address book.
 */
public class Appointment {

    public final String appointmentName;

    public Appointment(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        this.appointmentName = trimmedName;
        System.out.println(this.appointmentName);
    }

    @Override
    public String toString() {
        return appointmentName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.appointmentName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentName.hashCode();
    }

}
