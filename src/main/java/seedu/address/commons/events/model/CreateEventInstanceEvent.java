package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Indicates a new instance of a repaeted event should be added.
 */
public class CreateEventInstanceEvent extends BaseEvent {

    public final ReadOnlyEvent eventToAdd;
    public int period;

    public CreateEventInstanceEvent(ReadOnlyEvent event, int period) {
        this.eventToAdd = event;
        this.period = period;
    }

    @Override
    public String toString() {
        return "Event to add: " + eventToAdd.getTitle();
    }
}
