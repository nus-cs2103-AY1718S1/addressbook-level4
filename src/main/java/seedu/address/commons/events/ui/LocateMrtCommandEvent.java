package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An event requesting to view the address of the person specified on Google Map.
 */
//@@author YewOnn
public class LocateMrtCommandEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LocateMrtCommandEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
