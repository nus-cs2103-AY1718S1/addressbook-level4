package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

//@@author cjianhui
/**
 * Represents a Schedule's name in the address book.
 */
public class ScheduleName {

    public final String fullName;

    /**
     * Constructs a ScheduleName object
     */
    public ScheduleName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        this.fullName = trimmedName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleName // instanceof handles nulls
                && this.fullName.equals(((ScheduleName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
