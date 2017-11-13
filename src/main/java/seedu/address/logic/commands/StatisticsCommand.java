package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleStatisticsPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays the statistics of the address book to the user.
 */
public class StatisticsCommand extends Command {
    public static final String COMMAND_WORD = "statistics";
    public static final String COMMAND_ALIAS = "st";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the statistics of the address book to the user.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Listed statistics";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ToggleStatisticsPanelEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
