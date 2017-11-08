package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
//@@author pjunwei95
/**
 * An event requesting to backup the address book.
 */
public class BackUpEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public BackUpEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
