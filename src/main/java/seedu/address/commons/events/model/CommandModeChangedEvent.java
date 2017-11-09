package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.CommandMode;

//@@author tby1994
/**Indicates that Command Mode has changed*/
public class CommandModeChangedEvent extends BaseEvent {

    public final CommandMode commandMode;

    public CommandModeChangedEvent(CommandMode commandMode) {
        this.commandMode = commandMode;
    }

    @Override
    public String toString() {
        return "Command Mode: " + commandMode.toString();
    }
}
