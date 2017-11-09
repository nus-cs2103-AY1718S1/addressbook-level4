package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author seankwekjk
/**
 * Represents the user using the ToggleCommand
 */
public class ToggleChangedEvent extends BaseEvent {

    public ToggleChangedEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
