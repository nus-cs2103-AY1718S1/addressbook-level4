package seedu.address.commons.events.logic;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to delete a contact in AddressBook
 */

public class ContactDeletionEvent extends BaseEvent {

    public final int targetIndex;

    public ContactDeletionEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @ Override
    public String toString(){ return this.getClass().getSimpleName(); }
}
