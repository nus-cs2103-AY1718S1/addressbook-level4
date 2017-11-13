package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Represents an event where person has a missing display picture when he is supposed to have one.
 */
public class MissingDisplayPictureEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public MissingDisplayPictureEvent(ReadOnlyPerson person) {
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
