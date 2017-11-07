package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
//@@author Pengyuz
/** Indicates the Recyclebin in the model has changed*/
public class RecyclebinChangeEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public RecyclebinChangeEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size()
                + ", number of events " + data.getEventList().size();
    }
}
