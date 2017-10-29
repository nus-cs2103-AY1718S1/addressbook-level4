package seedu.address.model.clock;

import java.time.LocalDateTime;

/**
 * Represents a running clock inside the address book application.
 */
public class RunningClock {

    private String value;

    public RunningClock() {
        setCurrentTime();
    }

    public String getValue() {
        return value;
    }

    public int getHour() {
        return LocalDateTime.now().getHour();
    }

    public int getMinute() {
        return LocalDateTime.now().getMinute();
    }

    public int getSecond() {
        return LocalDateTime.now().getSecond();
    }

    public void setCurrentTime() {
        this.value = ((getHour() < 10) ? ("0" + getHour()) : getHour()) + ":"
                + ((getMinute() < 10) ? ("0" + getMinute()) : getMinute()) + ":"
                + ((getSecond() < 10) ? ("0" + getSecond()) : getSecond());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningClock // instanceof handles nulls
                && this.value.equals(((RunningClock) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
