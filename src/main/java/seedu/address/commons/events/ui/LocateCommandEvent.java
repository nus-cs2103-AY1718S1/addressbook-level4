package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;


public class LocateCommandEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LocateCommandEvent(ReadOnlyPerson person){
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
