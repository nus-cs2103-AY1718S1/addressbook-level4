package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;
//@@author blaqkrow
/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;
    private final int selectionIndex;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection, int selectionIndex) {
        this.newSelection = newSelection;
        this.selectionIndex = selectionIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
    public int getSelectionIndex() {
        return  selectionIndex; }
}
