package seedu.room.commons.events.model;

import seedu.room.commons.events.BaseEvent;
import seedu.room.model.ReadOnlyEventBook;

//@@author sushinoya
/** Indicates the EventBook in the model has changed*/
public class EventBookChangedEvent extends BaseEvent {

    public final ReadOnlyEventBook data;

    public EventBookChangedEvent(ReadOnlyEventBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
