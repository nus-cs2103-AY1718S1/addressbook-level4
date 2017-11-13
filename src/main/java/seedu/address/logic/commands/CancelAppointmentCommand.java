package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.AppointmentNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Eric
/**
 * Command to cancel an existing appointment
 */
public class CancelAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cancel";
    public static final String NO_SUCH_PERSON_FOUND = "No such person found";
    public static final String NO_SUCH_APPOINTMENT = "No such appointment found";
    public static final String MESSAGE_SUCCESS = "Appointment canceled.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels an appointment from a person. \n"
            + "Parameters: " + "DESCRIPTION with PERSON NAME \n"
            + "Example 1:" + COMMAND_WORD + " "
            + "Lunch with John Doe";
    public static final String REFER_PROMPT = "Please refer to the appointment description.";
    private String personString;
    private String appointmentString;

    public CancelAppointmentCommand(String person, String appointment) {
        this.personString = person;
        this.appointmentString = appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            ReadOnlyPerson person = getPersonFromName(personString);
            Appointment appointment = getAppointmentFromPerson(person, appointmentString);
            model.removeAppointment(person, appointment);
        } catch (PersonNotFoundException e) {
            throw new CommandException(NO_SUCH_PERSON_FOUND + "\n" + REFER_PROMPT);
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(NO_SUCH_APPOINTMENT + "\n" + REFER_PROMPT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Util method to search for the correct appointment from a person using only the description.
     * May have multiple appointments if there is same description under one person, but the first one will be deleted
     * @throws AppointmentNotFoundException if appointment not found
     */
    private Appointment getAppointmentFromPerson(ReadOnlyPerson person, String description)
            throws AppointmentNotFoundException {

        for (Appointment appointment : person.getAppointments()) {
            if (appointment.getDescription().equalsIgnoreCase(description.trim())) {
                return appointment;
            }
        }
        //Show Daily page for calendar
        EventsCenter.getInstance().post(new CalendarViewEvent('d'));
        throw new AppointmentNotFoundException();
    }

    /**
     * Extract person from address book using name. If there are more than one contact with the same name,
     * the first one will be extracted
     * @throws PersonNotFoundException if no such person is in the address book
     */
    private ReadOnlyPerson getPersonFromName(String personName) throws PersonNotFoundException {

        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            if (person.getName().toString().equalsIgnoreCase(personName.trim())) {
                return person;
            }
        }

        throw new PersonNotFoundException();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelAppointmentCommand // instanceof handles nulls
                && (this.appointmentString.equals(((CancelAppointmentCommand) other).appointmentString))
                && (this.personString.equals(((CancelAppointmentCommand) other).personString)));
    }
}
