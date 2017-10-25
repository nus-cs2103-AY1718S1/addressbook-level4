package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a click on one of the names in Insurance Profile
 */
public class PersonNameClickedEvent extends BaseEvent {


    private final ReadOnlyPerson target;

    public PersonNameClickedEvent(ReadOnlyPerson target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return target;
    }
}

