package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

//@@author archthegit

/**
 * Event to handle info change in the case of edit
 */

public class PersonSelectionChangedEvent extends BaseEvent {

    private final ReadOnlyPerson newSelection;

    public PersonSelectionChangedEvent(ReadOnlyPerson newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return newSelection;
    }
}


