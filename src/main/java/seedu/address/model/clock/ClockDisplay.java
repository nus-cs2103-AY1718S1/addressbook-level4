package seedu.address.model.clock;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the clock display on the GUI of the address book application.
 */
public class ClockDisplay {

    private ObjectProperty<RunningClock> clock;

    public ClockDisplay() {
        this.clock = new SimpleObjectProperty<>(new RunningClock());
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

    @Override
    public String toString() {
        return clock.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockDisplay // instanceof handles nulls
                && this.clock.equals(((ClockDisplay) other).clock)); // state check
    }

    @Override
    public int hashCode() {
        return clock.hashCode();
    }

}
