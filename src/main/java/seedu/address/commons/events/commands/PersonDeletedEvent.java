package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

//@@author frozventus
/** Indicates the AddressBook in the display list is now filtered*/
public class PersonDeletedEvent extends BaseEvent {

    public PersonDeletedEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

