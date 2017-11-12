package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Calendar;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Eric
/**
 * Command to add appointment to a person in addressBook
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for add. \n"
            + "Parameters: " + COMMAND_WORD + " INDEX "
            + PREFIX_DATE + "DESCRIPTION, TIME" + "\n"
            + "Example 1:" + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "Lunch, Next Monday 3pm";

    public static final String MESSAGE_SUCCESS = "New appointment added. ";
    public static final String INVALID_PERSON = "This person is not in your address book";
    public static final String INVALID_DATE = "Invalid Date. Please enter a valid date.";
    public static final String SORT_APPOINTMENT_FEEDBACK = "Rearranged contacts to show upcoming appointments.";


    private final Index index;
    private final Appointment appointment;

    public AddAppointmentCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        requireNonNull(index);
        requireNonNull(appointment);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddAppointment = lastShownList.get(index.getZeroBased());

        if (appointment.getDate() != null && !isDateValid()) {
            return new CommandResult(INVALID_DATE);
        }

        try {
            model.addAppointment(personToAddAppointment, appointment);
        } catch (PersonNotFoundException e) {
            return new CommandResult(INVALID_PERSON);
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * Checks if appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(appointment);
        Calendar calendar = Calendar.getInstance();
        return !appointment.getDate().before(calendar.getTime());
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && (this.index.getZeroBased() == ((AddAppointmentCommand) other).index.getZeroBased())
                && (this.appointment.equals(((AddAppointmentCommand) other).appointment)));
    }

    /**
     * For testing purposes
     *
     */
    public void setData(Model model) {
        this.model = model;
    }

}
