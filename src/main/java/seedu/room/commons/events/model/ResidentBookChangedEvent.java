package seedu.room.commons.events.model;

import seedu.room.commons.events.BaseEvent;
import seedu.room.model.ReadOnlyResidentBook;

/** Indicates the ResidentBook in the model has changed*/
public class ResidentBookChangedEvent extends BaseEvent {

    public final ReadOnlyResidentBook data;

    public ResidentBookChangedEvent(ReadOnlyResidentBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
