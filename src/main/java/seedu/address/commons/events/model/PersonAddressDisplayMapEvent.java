package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl
/** Indicates a person's address as a map is to be displayed*/
public class PersonAddressDisplayMapEvent extends BaseEvent {

    public final ReadOnlyPerson person;
    public final int targetIndex;

    public PersonAddressDisplayMapEvent (ReadOnlyPerson person, int targetIndex) {
        this.person = person;
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return "Displaying location of " + person.getName() + ": " + person.getAddress();
    }
}
