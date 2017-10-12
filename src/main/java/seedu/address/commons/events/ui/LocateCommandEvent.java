package seedu.address.commons.events.ui;

import seedu.address.model.person.Person;
import seedu.address.commons.events.BaseEvent;


public class LocateCommandEvent extends BaseEvent{

    private Person person;

    public LocateCommandEvent(Person person){
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getPerson() {
        return person;
    }
}