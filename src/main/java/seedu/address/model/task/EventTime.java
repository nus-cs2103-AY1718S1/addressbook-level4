package seedu.address.model.task;

public class EventTime {

    public final String time;

    public EventTime(String time) {
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


    //public boolean areTimesValid

    //publ
}
