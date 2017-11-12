package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Suffix;

/**
 * Represents a recurring date in the application.
 */
public class RecurInterval {


    public final String recurInterval;

    /**
     * Validates given starting date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public RecurInterval(Suffix recurInterval) throws IllegalValueException {
        requireNonNull(recurInterval);
        this.recurInterval = recurInterval.toString();
    }

    @Override
    public String toString() {
        return recurInterval;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurInterval // instanceof handles nulls
                && this.recurInterval.equals(((RecurInterval) other).recurInterval)); // state check
    }

    @Override
    public int hashCode() {
        return recurInterval.hashCode();
    }

}
