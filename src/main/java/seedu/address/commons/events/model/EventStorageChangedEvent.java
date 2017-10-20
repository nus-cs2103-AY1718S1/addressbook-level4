package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEventStorage;

/** Indicates the EventStorage in the model has changed*/
public class EventStorageChangedEvent extends BaseEvent {

    public final ReadOnlyEventStorage data;

    public EventStorageChangedEvent(ReadOnlyEventStorage data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
