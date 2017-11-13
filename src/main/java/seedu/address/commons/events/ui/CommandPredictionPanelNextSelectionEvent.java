package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates a request to go the next selection in the CommandPredictionPanel
 */
public class CommandPredictionPanelNextSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
