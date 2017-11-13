package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl

/**
 * Represents a modification to a person.
 */
public class PersonHasBeenModifiedEvent extends BaseEvent {

    public final ReadOnlyPerson oldPerson;
    public final ReadOnlyPerson newPerson;

    public PersonHasBeenModifiedEvent (ReadOnlyPerson oldPerson, ReadOnlyPerson newPerson) {
        this.oldPerson = oldPerson;
        this.newPerson = newPerson;
    }

    @Override
    public String toString() {
        return "Person has been modified: " + newPerson.getName().toString();
    }
}
