package seedu.address.logic.commands;


import seedu.address.Quickstart;

/**
 * Returns the google calendar of the person
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_SUCCESS = "Calendar Retrieved Successfully!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
