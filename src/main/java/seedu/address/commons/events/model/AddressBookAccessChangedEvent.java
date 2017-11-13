package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Zzmobie
/** Indicates that an entry of AddressBook in the model has changed*/
public class AddressBookAccessChangedEvent extends BaseEvent {

    public final ReadOnlyPerson personToEdit;

    public AddressBookAccessChangedEvent(ReadOnlyPerson personToEdit) {
        this.personToEdit = personToEdit;
    }

    @Override
    public String toString() {
        return "Entry to increment: " + personToEdit.toString();
    }
}
