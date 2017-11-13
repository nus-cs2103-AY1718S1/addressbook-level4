package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

//@@author eldriclim
/**
 * Represents an update in address book master event list
 */
public class ScheduleUpdateEvent extends BaseEvent {

    private ObservableList<Event> events;

    public ScheduleUpdateEvent(ObservableList<Event> events) {
        this.events = events;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
