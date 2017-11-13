package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

//@@author khooroko
/**
 * Represents a selection change in the Nearby Person List Panel
 */
public class NearbyPersonPanelSelectionChangedEvent extends BaseEvent {

    private final PersonCard newSelection;

    public NearbyPersonPanelSelectionChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
