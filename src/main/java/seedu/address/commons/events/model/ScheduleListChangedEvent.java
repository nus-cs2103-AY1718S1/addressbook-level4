package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.schedule.Schedule;

//@@author limcel
/**
 * Represents a Change in the current FilteredPersonList
 */
public class ScheduleListChangedEvent extends BaseEvent {

    private final ObservableList<Schedule> currentList;

    public ScheduleListChangedEvent(ObservableList<Schedule> currentFilteredList) {
        this.currentList = currentFilteredList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Schedule> getCurrentScheduleList() {
        return currentList;
    }
}
