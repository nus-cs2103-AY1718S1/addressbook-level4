package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author RonakLakhotia
/**
 * Raises a FaceBookEvent when the user executes the facebook command to view the profile page.
 */
public class FaceBookEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public FaceBookEvent(ReadOnlyPerson person) {
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
