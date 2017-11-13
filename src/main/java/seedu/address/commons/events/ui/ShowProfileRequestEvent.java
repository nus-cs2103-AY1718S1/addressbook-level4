//@@author duyson98

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Indicates a request to view the profile of a person in filtered list.
 */
public class ShowProfileRequestEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowProfileRequestEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
