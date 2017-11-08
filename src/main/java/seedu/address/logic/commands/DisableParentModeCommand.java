package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ModelToggleEvent;

//@@author deep4k
/**
 * Disables all parent commands and only enables child command
 */
public class DisableParentModeCommand extends Command {

    public static final String COMMAND_WORD = "disable.p";

    public static final String MESSAGE_SUCCESS = "Only child commands are enabled!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.parentDisabled));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

