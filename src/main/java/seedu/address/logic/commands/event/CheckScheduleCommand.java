package seedu.address.logic.commands.event;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowCalendarEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Show all the events in this current week in a calendar view.
 */
public class CheckScheduleCommand extends Command {

    public static final String COMMAND_WORD = "thisweek";

    public static final String MESSAGE_SUCCESS = "Shown schedule for this week";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowCalendarEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
