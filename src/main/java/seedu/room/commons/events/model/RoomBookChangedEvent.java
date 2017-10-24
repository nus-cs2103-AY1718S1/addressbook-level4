package seedu.room.commons.events.model;

import seedu.room.commons.events.BaseEvent;
import seedu.room.model.ReadOnlyRoomBook;

/** Indicates the RoomBook in the model has changed*/
public class RoomBookChangedEvent extends BaseEvent {

    public final ReadOnlyRoomBook data;

    public RoomBookChangedEvent(ReadOnlyRoomBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
