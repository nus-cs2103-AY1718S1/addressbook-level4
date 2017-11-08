//@@author namvd2709
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.appointment.Appointment.MESSAGE_DATETIME_CONSTRAINT;
import static seedu.address.model.appointment.Appointment.isAfterToday;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Appoint command which add an appointment to the calendar
 */
public class AppointCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appoint";
    public static final String MESSAGE_APPOINT_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Appointment removed for person: %1$s";
    public static final String MESSAGE_APPOINTMENT_CLASH = "Appointment clash with another in address book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an appointment to a person to the address book "
            + "by the index number in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[date (dd/mm/yyyy)] [time (hh:mm)] [duration (mins)";

    private final Index index;
    private final Appointment appointment;

    public AppointCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);

        this.index = index;
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        Set<Appointment> appointments = model.getAllAppointments();
        UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), appointment, personToEdit.getProfilePicture(),
                personToEdit.getGroups(), personToEdit.getTags());

        if (!personToEdit.getAppointment().value.equals("")) {
            appointments.remove(personToEdit.getAppointment());
        }
        uniqueAppointmentList.setAppointments(appointments);

        if (uniqueAppointmentList.hasClash(appointment)) {
            throw new CommandException(MESSAGE_APPOINTMENT_CLASH);
        }

        if (!appointment.value.equals("") && !isAfterToday(appointment.getStart())) {
            throw new CommandException(MESSAGE_DATETIME_CONSTRAINT);
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("Person cannot be duplicated.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generate corresponding success message
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!appointment.value.isEmpty()) {
            return String.format(MESSAGE_APPOINT_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, personToEdit);
        }
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
        return index.equals(e.index)
            && appointment.equals(e.appointment);
    }
}
