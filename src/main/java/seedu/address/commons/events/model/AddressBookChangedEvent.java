package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.storage.AddressBookData;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final AddressBookData data;

    public AddressBookChangedEvent(AddressBookData data) {
        this.data =  data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getAddressBook().getPersonList().size() + ", number of tags "
                + data.getAddressBook().getTagList().size();
    }
}
