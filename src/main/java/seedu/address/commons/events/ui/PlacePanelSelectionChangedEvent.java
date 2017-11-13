package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PlaceCard;

/**
 * Represents a selection change in the Place List Panel
 */
public class PlacePanelSelectionChangedEvent extends BaseEvent {


    private final PlaceCard newSelection;

    public PlacePanelSelectionChangedEvent(PlaceCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PlaceCard getNewSelection() {
        return newSelection;
    }
}
