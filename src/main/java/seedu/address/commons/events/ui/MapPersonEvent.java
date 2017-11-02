package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a mapping function call by user
 */
public class MapPersonEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public MapPersonEvent(ReadOnlyPerson person) {
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
