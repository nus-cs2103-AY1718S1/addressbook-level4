package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author blaqkrow
/**
 * Represents a selection change in the Qr Event
 */
public class QrSaveEvent extends BaseEvent {
    private ReadOnlyPerson person;
    public QrSaveEvent(ReadOnlyPerson person) {
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
