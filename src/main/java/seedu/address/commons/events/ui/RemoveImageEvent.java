package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author liliwei25
/**
 * Represents a image removing function call by user
 */
public class RemoveImageEvent extends BaseEvent {
    private final ReadOnlyPerson person;

    public RemoveImageEvent(ReadOnlyPerson person) {
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
