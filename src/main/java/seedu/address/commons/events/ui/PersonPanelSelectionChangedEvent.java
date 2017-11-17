package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;

    //@@author LinusMelb
    private final int backToHomePageValue = 0;
    //@@author

    public PersonPanelSelectionChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }

    //@@author LinusMelb
    public int getBackToHomePageValue() {
        return this.backToHomePageValue;
    }
    //@@author

}
