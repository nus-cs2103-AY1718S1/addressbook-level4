package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ModelToggleEvent;
import seedu.address.commons.events.ui.ToggleParentChildModeEvent;

//@@author Alim95
/**
 * Enables all commands for the user
 */
public class ParentModeCommand extends Command {

    public static final String COMMAND_WORD = "parent";

    public static final String MESSAGE_SUCCESS = "All commands are enabled!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.parentEnabled));
        EventsCenter.getInstance().post(new ToggleParentChildModeEvent(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
