package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates that a person has been edited.
 */
public class PersonEditedEvent extends BaseEvent {

    public final ReadOnlyPerson editedPerson;
    public final int targetIndex;

    public PersonEditedEvent(ReadOnlyPerson person, Index index) {
        editedPerson = person;
        targetIndex = index.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
