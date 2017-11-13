//@@author tshradheya
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonCard;

/**
 * Indicates a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;
    private final ReadOnlyPerson person;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection, ReadOnlyPerson person) {
        this.newSelection = newSelection;
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
