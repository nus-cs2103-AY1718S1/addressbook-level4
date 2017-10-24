package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Appoint command which add an appointment to the calendar
 */
public class AppointCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appoint";
    public static final String MESSAGE_APPOINT_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_ARGUMENTS = "Method takes two arguments";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an appointment to a person to the address book "
            + "by the index number in the last person listing."
            + "Parameters: INDEX (must be a positive integer) "
            + "[date (dd/mm/yy)] [time (hh:mm)] [duration (mins)";

    private final Index index;
    private final String appointmentDetails;

    public AppointCommand(Index index, String appointmentDetails) {
        requireNonNull(index);
        requireNonNull(appointmentDetails);

        this.index = index;
        this.appointmentDetails = appointmentDetails;
    }

    public String getAppointment() {
        return this.appointmentDetails;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_ARGUMENTS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointCommand)) {
            return false;
        }

        // state check
        AppointCommand e = (AppointCommand) other;
        return getAppointment().equals(e.getAppointment());
    }
}
