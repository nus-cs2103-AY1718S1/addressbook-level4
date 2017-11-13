package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

//@@author khooroko
/**
 * Represents a selection change in the Nearby Person List Panel to a person that is not in the current Person List
 * Panel.
 */
public class NearbyPersonNotInCurrentListEvent extends BaseEvent {

    private final PersonCard newSelection;

    public NearbyPersonNotInCurrentListEvent(PersonCard newSelection) {
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
