package seedu.address.logic.commands;

//@@author Eric
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Command to change calendar view
 */
public class CalendarViewCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes calendar view. \n"
            + COMMAND_ALIAS + ": Short hand equivalent for calendar. \n"
            + "Parameter: \n"
            + "Day view: d\n"
            + "Week view: w\n"
            + "Month view: m\n"
            + "Year view: y\n";

    public static final String MESSAGE_SUCCESS = "View changed.";

    private Character arg;

    public CalendarViewCommand(Character c) {
        this.arg = c;
    }
    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new CalendarViewEvent(arg));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
