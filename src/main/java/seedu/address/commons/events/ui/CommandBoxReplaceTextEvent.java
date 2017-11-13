package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author zameschua
/**
 * Indicates a request to change the text in the {@link seedu.address.ui.CommandBox}
 */
public class CommandBoxReplaceTextEvent extends BaseEvent {

    private String text;

    public CommandBoxReplaceTextEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
