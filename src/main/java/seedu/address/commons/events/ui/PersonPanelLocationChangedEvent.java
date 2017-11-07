//@@author erik0704
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelLocationChangedEvent extends BaseEvent {


    private final ReadOnlyPerson newSelection;

    public PersonPanelLocationChangedEvent(ReadOnlyPerson newSelection) {
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
