package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author zacharytang
/**
 * Represents a person being selected
 */
public class PersonSelectedEvent extends BaseEvent {

    public final ReadOnlyPerson person;
    public final int index;

    public PersonSelectedEvent(ReadOnlyPerson person, int index) {
        this.person = person;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Person selected: " + person.getName().toString();
    }
}
