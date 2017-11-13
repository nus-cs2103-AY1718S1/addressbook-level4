package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyRolodex;

/** Indicates the Rolodex in the model has changed*/
public class RolodexChangedEvent extends BaseEvent {

    public final ReadOnlyRolodex data;

    public RolodexChangedEvent(ReadOnlyRolodex data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
