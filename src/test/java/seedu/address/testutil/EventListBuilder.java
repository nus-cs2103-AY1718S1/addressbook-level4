package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

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
