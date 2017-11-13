package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates the selecting of a Command Prediction in the {@link seedu.address.ui.CommandPredictionPanel}
 */
public class CommandPredictionPanelSelectionEvent extends BaseEvent {

    public CommandPredictionPanelSelectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
