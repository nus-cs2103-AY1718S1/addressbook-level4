package seedu.address.model.association;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A pair contains one person and one event
 */
public class PersonEventPair {

    private Person person;
    private Event event;

    public PersonEventPair(Person person, Event event) {
        this.person = person;
        this.event = event;
    }

    public Person getPerson() {
        return person;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonEventPair // instanceof handles nulls
                && this.event.equals(((PersonEventPair) other).getEvent())
                && this.person.equals(((PersonEventPair) other).getPerson()));
    }
}
