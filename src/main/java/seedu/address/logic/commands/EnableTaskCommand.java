package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ModelToggleEvent;
import seedu.address.commons.events.ui.ToggleToTaskViewEvent;

/**
 * Enables task commands for the user
 */
public class EnableTaskCommand extends Command {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_SUCCESS = "Showing all tasks and only task commands are enabled";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.taskEnabled));
        EventsCenter.getInstance().post(new ToggleToTaskViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
