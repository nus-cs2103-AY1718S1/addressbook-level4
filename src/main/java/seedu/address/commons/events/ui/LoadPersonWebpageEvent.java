//@@author tshradheya
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates 'select' command's successful execution
 * Triggers event to load person's webpage
 */
public class LoadPersonWebpageEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LoadPersonWebpageEvent(ReadOnlyPerson person) {
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
