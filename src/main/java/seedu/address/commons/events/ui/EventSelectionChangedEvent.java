package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.ReadOnlyEvent;

//@@author DarrenCzen
/**
 * Event to handle info change in the case of edit
 */
public class EventSelectionChangedEvent extends BaseEvent {
    private final ReadOnlyEvent newSelection;

    public EventSelectionChangedEvent(ReadOnlyEvent newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return newSelection;
    }
}
