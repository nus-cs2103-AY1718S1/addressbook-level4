package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl

/**
 * Represents a deletion of a person.
 */
public class PersonHasBeenDeletedEvent extends BaseEvent {

    public final ReadOnlyPerson deletedPerson;

    public PersonHasBeenDeletedEvent (ReadOnlyPerson deletedPerson) {
        this.deletedPerson = deletedPerson;
    }

    @Override
    public String toString() {
        return "Person has been deleted: " + deletedPerson.getName().toString();
    }
}
