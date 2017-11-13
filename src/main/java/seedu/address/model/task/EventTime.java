package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

/**
 * Represents the time of an event or task in the task manager.
 * Guarantees: immutable.
 */
public class EventTime {

    public final String time;

    public EventTime(String time) {
        requireNonNull(time);
        String trimmedTime = time.trim();
        this.time = trimmedTime;
    }

    public boolean isPresent() {
        return !time.isEmpty();
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTime // instanceof handles nulls
                && this.time.equals(((EventTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
