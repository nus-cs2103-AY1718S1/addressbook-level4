package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author DarrenCzen
/**
 * event to unselect an event card in the case of a delete
 */
public class EventPanelUnselectEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
