# namvd2709
###### /java/seedu/address/logic/commands/AppointCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an appointment to a person to the address book "
            + "by the index number in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[date (dd/mm/yy)] [time (hh:mm)] [duration (mins)";

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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), appointment,
                personToEdit.getGroups(), personToEdit.getTags());

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
```
###### /java/seedu/address/logic/parser/AppointCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Parses input arguments and creates a new AppointCommand object
 */
public class AppointCommandParser implements Parser<AppointCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AppointCommand
     * and returns an AppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPOINT);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_APPOINT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE));
        }

        Index index;
        String appointmentDetails = argumentMultimap.getValue(PREFIX_APPOINT).orElse("");

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            return new AppointCommand(index, new Appointment(appointmentDetails));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/model/appointment/Appointment.java
``` java
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/MM/yyyy hh:mm duration, the date must be older than today";
    public static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String MESSAGE_DURATION_CONSTRAINT = "Duration must be an integer";
    private static final String MESSAGE_DATETIME_CONSTRAINT = "Date time must be valid format, dd/MM/yyyy HH:mm";

    public final String value;
    public final LocalDateTime start;
    public final LocalDateTime end;

    /**
     * Validates given appointment.
    */
    public Appointment(String appointment) throws IllegalValueException {
        requireNonNull(appointment);
        if (appointment.equals("")) {
            this.value = appointment;
            this.start = null;
            this.end = null;
        } else {
            try {
                String[] split = appointment.split("\\s+");
                String date = split[0];
                String time = split[1];
                String duration = split[2];
                if (!isValidDuration(duration)) {
                    throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINT);
                }
                LocalDateTime startDateTime = getDateTime(date + " " + time);
                LocalDateTime endDateTime = getEndDateTime(startDateTime, duration);
                if (!isAfterToday(startDateTime) || !isAfterToday(endDateTime)) {
                    throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINT);
                }
                this.value = appointment;
                this.start = startDateTime;
                this.end = endDateTime;
            } catch (ArrayIndexOutOfBoundsException iob) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static LocalDateTime getDateTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return LocalDateTime.parse(datetime, formatter);
    }

    /**
     * Returns true if a given string is a valid appointment.
     */
    public static boolean isAfterToday(LocalDateTime datetime) {
        LocalDateTime now = LocalDateTime.now();
        return datetime.isAfter(now);
    }

    /**
     * Returns true if given duration is an integer, using regex
     */
    public static boolean isValidDuration(String duration) {
        return duration.matches("^-?\\d+$");
    }

    /**
     * Assumes startDateTime and duration are valid
     * @param startDateTime dd/MM/yyyy format
     * @param duration must be integer
     * @return String end date and time of appointment
     */
    private static LocalDateTime getEndDateTime(LocalDateTime startDateTime, String duration) {
        return startDateTime.plusMinutes(Integer.parseInt(duration));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.value.equals(((Appointment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/appointment/UniqueAppointmentList.java
``` java
package seedu.address.model.appointment;

/**
 * Class makes sure that there is no clash in appointment
 */
public class UniqueAppointmentList {
}
```
