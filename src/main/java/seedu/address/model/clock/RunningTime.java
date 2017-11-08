//@@author duyson98

package seedu.address.model.clock;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a running time inside the address book application.
 */
public class RunningTime {

    private int hour;
    private int minute;
    private int second;

    public RunningTime() {
        setCurrentTime();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setCurrentTime() {
        this.hour = LocalDateTime.now().getHour();
        this.minute = LocalDateTime.now().getMinute();
        this.second = LocalDateTime.now().getSecond();
    }

    @Override
    public String toString() {
        return ((hour < 10) ? ("0" + hour) : hour) + ":"
                + ((minute < 10) ? ("0" + minute) : minute) + ":"
                + ((second < 10) ? ("0" + second) : second);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningTime // instanceof handles nulls
                && this.hour == ((RunningTime) other).hour // state checks onwards
                && this.minute == ((RunningTime) other).minute
                && this.second == ((RunningTime) other).second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, second);
    }

}
