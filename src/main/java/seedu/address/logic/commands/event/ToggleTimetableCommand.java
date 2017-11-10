package seedu.address.logic.commands.event;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleTimetableEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

//@@author reginleiff
/**
 * Toggles the view of the timetable.
 */
public class ToggleTimetableCommand extends Command {

    public static final String COMMAND_WORD = "timetable";

    public static final String MESSAGE_SUCCESS = "Timetable toggled!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ToggleTimetableEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
