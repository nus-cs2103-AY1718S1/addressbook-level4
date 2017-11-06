//@@author duyson98

package seedu.address.model.clock;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the clock display on the GUI of the address book application.
 */
public class ClockDisplay {

    private ObjectProperty<RunningTime> time;
    private ObjectProperty<RunningDate> date;

    public ClockDisplay() {
        this.time = new SimpleObjectProperty<>(new RunningTime());
        this.date = new SimpleObjectProperty<>(new RunningDate());
    }

    public void setTime(RunningTime time) {
        this.time.set(time);
    }

    public ObjectProperty<RunningTime> timeProperty() {
        return time;
    }

    public RunningTime getTime() {
        return time.get();
    }

    public void setDate(RunningDate date) {
        this.date.set(date);
    }

    public ObjectProperty<RunningDate> dateProperty() {
        return date;
    }

    public RunningDate getDate() {
        return date.get();
    }

    public String getTimeAsText() {
        return time.get().toString();
    }

    public String getDateAsText() {
        return date.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockDisplay // instanceof handles nulls
                && this.time.equals(((ClockDisplay) other).time) // state checks onwards
                && this.date.equals(((ClockDisplay) other).date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, date);
    }

}
