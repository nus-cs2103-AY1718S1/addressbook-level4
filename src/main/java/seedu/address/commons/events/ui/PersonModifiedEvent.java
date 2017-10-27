package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates that a person has been edited.
 */
public class PersonModifiedEvent extends BaseEvent {

    private final ReadOnlyPerson modifiedPerson;

    public PersonModifiedEvent(ReadOnlyPerson person) {
        modifiedPerson = person;
    }

    public ReadOnlyPerson getModifiedPerson() {
        return modifiedPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
