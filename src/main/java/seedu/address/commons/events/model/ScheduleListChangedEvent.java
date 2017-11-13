package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.schedule.Schedule;

//@@author limcel
/**
 * Represents a change in the current schedule list
 */
public class ScheduleListChangedEvent extends BaseEvent {

    private final ObservableList<Schedule> currentList;

    public ScheduleListChangedEvent(ObservableList<Schedule> currentList) {
        this.currentList = currentList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
