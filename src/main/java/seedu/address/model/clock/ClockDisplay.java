//@@author duyson98

package seedu.address.model.clock;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the clock display on the GUI of the address book application.
 */
public class ClockDisplay {

    private ObjectProperty<RunningClock> clock;
    private ObjectProperty<RunningDate> date;

    public ClockDisplay() {
        this.clock = new SimpleObjectProperty<>(new RunningClock());
        this.date = new SimpleObjectProperty<>(new RunningDate());
    }

    public void setClock(RunningClock clock) {
        this.clock.set(clock);
    }

    public ObjectProperty<RunningClock> clockProperty() {
        return clock;
    }

    public RunningClock getClock() {
        return clock.get();
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
        return clock.get().toString();
    }

    public String getDateAsText() {
        return date.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockDisplay // instanceof handles nulls
                && this.clock.equals(((ClockDisplay) other).clock) // state checks onwards
                && this.date.equals(((ClockDisplay) other).date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(clock, date);
    }

}
