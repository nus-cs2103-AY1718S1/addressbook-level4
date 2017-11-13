package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a change of input in command box
 * Contains the full string of input in the command box
 */

//@@author nicholaschuayunzhi
public class CommandInputChangedEvent extends BaseEvent {

    public final String currentInput;

    public CommandInputChangedEvent(String newCurrentInput) {
        currentInput = newCurrentInput;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
