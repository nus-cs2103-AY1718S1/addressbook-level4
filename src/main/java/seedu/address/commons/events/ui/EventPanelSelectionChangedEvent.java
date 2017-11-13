package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.EventCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final EventCard newSelection;

    public EventPanelSelectionChangedEvent(EventCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EventCard getNewSelection() {
        return newSelection;
    }
}
