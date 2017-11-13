package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author eldonng

/**
 * Indicates that Person's information has been changed
 */
public class NewPersonInfoEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public NewPersonInfoEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
