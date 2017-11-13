package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates change in the text of the CommandBox
 */
public class CommandBoxContentsChangedEvent extends BaseEvent {

    private String newCommandText;

    public CommandBoxContentsChangedEvent(String newCommandText) {
        this.newCommandText = newCommandText;
    }

    public String getCommandText() {
        return newCommandText;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
