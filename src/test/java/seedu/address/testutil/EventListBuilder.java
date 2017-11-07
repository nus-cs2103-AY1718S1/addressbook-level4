package seedu.address.testutil;
// @@author HuWanqing
import seedu.address.model.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * A utility class help to build an event list
 */
public class EventListBuilder {
    private EventList eventList;

    public EventListBuilder() {
        eventList = new EventList();
    }

    public EventListBuilder(EventList eventList) {
        this.eventList = eventList;
    }

    /**
     * Adds a new {@code Event} to the {@code EventList} that we are building.
     */
    public EventListBuilder withEvent(ReadOnlyEvent event) {
        try {
            eventList.addEvent(event);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }

    public EventList build() {
        return eventList;
    }
}
