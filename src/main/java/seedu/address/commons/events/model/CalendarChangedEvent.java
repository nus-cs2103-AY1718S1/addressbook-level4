package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
//@@author tby1994
/** Indicates the calendar in the model has changed*/
public class CalendarChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public CalendarChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size()
                + "calendar has changed";
    }
}
