package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

//@@author deep4k

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {

    private final PersonCard newSelection;
    private final PersonCard oldSelection;
    private final String selectedIndex;

    public PersonPanelSelectionChangedEvent(PersonCard oldSelection, PersonCard newSelection) {
        this.oldSelection = oldSelection;
        this.newSelection = newSelection;
        this.selectedIndex = newSelection.getPersonCardIndex();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }

    public PersonCard getOldSelection() {
        return oldSelection;
    }

    public String getSelectedIndex() {
        return selectedIndex;
    }
}
