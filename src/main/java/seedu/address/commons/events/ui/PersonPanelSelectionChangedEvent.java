package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ParcelCard;

/**
 * Represents a selection change in the Parcel List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ParcelCard newSelection;

    public PersonPanelSelectionChangedEvent(ParcelCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ParcelCard getNewSelection() {
        return newSelection;
    }
}
