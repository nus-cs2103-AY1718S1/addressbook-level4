package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowCalendarRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Locates a person's address by showing its location on Google Maps.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cl";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ":Display calendar)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_DISPLAY_CALENDAR_SUCCESS = "Displaying Calendar...";


    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new ShowCalendarRequestEvent());
        return new CommandResult(MESSAGE_DISPLAY_CALENDAR_SUCCESS);

    }

}
