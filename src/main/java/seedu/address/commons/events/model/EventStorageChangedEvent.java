package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventList;

/** Indicates the EventStorage in the model has changed*/
public class EventStorageChangedEvent extends BaseEvent {

    public final ReadOnlyEventList data;

    public EventStorageChangedEvent(ReadOnlyEventList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
