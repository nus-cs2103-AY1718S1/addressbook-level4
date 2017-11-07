package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMrtRequestEvent;

//@@author aungmyin23
/**
 * Format full help instructions for every command for display.
 */
public class MrtCommand extends Command {

    public static final String COMMAND_WORD = "mrt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows MRT map.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_MRT_MESSAGE = "Opened mrt window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMrtRequestEvent());
        return new CommandResult(SHOWING_MRT_MESSAGE);
    }
}
