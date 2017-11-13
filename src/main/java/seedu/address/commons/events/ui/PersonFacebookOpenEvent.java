//@@author heiseish
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a facebook open request
 */
public class PersonFacebookOpenEvent extends BaseEvent {


    private final ReadOnlyPerson newSelection;

    public PersonFacebookOpenEvent(ReadOnlyPerson newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return newSelection;
    }
}
