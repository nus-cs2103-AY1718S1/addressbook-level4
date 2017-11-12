package seedu.room.testutil;

import seedu.room.model.EventBook;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;

//@@author sushinoya
/**
 * A utility class to help with building EventBook objects.
 * Example usage: <br>
 * {@code EventBook ab = new EventBookBuilder().withEvent(USPolymath).build();}
 */
public class EventBookBuilder {

    private EventBook eventBook;

    public EventBookBuilder() {
        eventBook = new EventBook();
    }

    public EventBookBuilder(EventBook eventBook) {
        this.eventBook = eventBook;
    }

    /**
     * Adds a new {@code Event} to the {@code EventBook} that we are building.
     */
    public EventBookBuilder withEvent(ReadOnlyEvent event) {
        try {
            eventBook.addEvent(event);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }


    public EventBook build() {
        return eventBook;
    }
}
