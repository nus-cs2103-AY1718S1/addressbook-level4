package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to delete a contact in AddressBook
 */

public class ContactAltDeletionEvent extends BaseEvent {

    public final String targetName;

    public ContactAltDeletionEvent(String targetName) {
        this.targetName = targetName;
    }

    @ Override
    public String toString(){ return this.getClass().getSimpleName(); }
}
