package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Appoint command which add an appointment to the calendar
 */
public class AppointCommand extends UndoableCommand {
    
    public static final String COMMAND_WORD = "appoint";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an appointment to a person to the address book "
            + "by the index number in the last person listing."
            + "Parameters: INDEX (must be a positive integer) "
            + "[date (ddmmyy)] [time (hhmm)] [duration (mins)";
    
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Not implemented");
    }
}
