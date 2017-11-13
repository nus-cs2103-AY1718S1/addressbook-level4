package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author hengyu95
/** Indicates that a backup command was used*/
public class BackupAddressBookEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public BackupAddressBookEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
