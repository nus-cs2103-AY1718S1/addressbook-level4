package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarRequestEvent;

//@@author yilun-zhu
/**
 * Opens the calendar panel.
 */

public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String MESSAGE_SUCCESS = "Calendar loaded!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows calendar ";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarRequestEvent());
        return new CommandResult(getMessageForCalendar());
    }
}
