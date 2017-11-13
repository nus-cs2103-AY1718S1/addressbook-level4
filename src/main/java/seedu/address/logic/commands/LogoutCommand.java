package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutEvent;
//@@author yangminxingnus
/**
 * Command of logout.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Logout succeeded.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new LogoutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
