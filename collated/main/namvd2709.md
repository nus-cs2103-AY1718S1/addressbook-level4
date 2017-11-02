# namvd2709
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "EF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true
     *       </pre>
     * @param sentence cannot be null
     * @param phrase cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsStringIgnoreCase(String sentence, String phrase) {
        requireNonNull(sentence);
        requireNonNull(phrase);

        String preppedWord = phrase.trim();
        checkArgument(!preppedWord.isEmpty(), "Search phrase parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Search phrase parameter should be a single word");

        return sentence.toLowerCase().contains(phrase.toLowerCase());
    }
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "EF") == false // no word starts with ef
     *       containsWordIgnoreCase("ABc def", "aB") == true
     *       </pre>
     * @param sentence cannot be null
     * @param phrase cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordsStartWithString(String sentence, String phrase) {
        requireNonNull(sentence);
        requireNonNull(phrase);

        String preppedWord = phrase.trim();
        checkArgument(!preppedWord.isEmpty(), "Search phrase parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Search phrase parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.toLowerCase().startsWith(preppedWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\logic\AutocompleteManager.java
``` java
package seedu.address.logic;

import java.util.ArrayList;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Manage autocomplete logic when typing commands
 */
public class AutocompleteManager {

    // This should include all commands.
    // Add new commands here if implemented
    private String[] commands = new String[]{
        AddCommand.COMMAND_WORD, AppointCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
        GroupCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD
    };

    public AutocompleteManager() {}

```
###### \java\seedu\address\logic\AutocompleteManager.java
``` java
    /**
     * attempt to autocomplete input into one of the commands
     * @param matcher field input
     * @return String representation of command, or the matcher if not exactly one command is returned.
     */
    public String attemptAutocomplete(String matcher) {
        ArrayList<String> matches = new ArrayList<>();
        for (String command: commands) {
            if (StringUtil.containsWordsStartWithString(command, matcher)) {
                matches.add(command);
            }
        }
        if (matches.size() == 1) {
            return matches.get(0);
        } else {
            return matcher;
        }
    }
}
```
###### \java\seedu\address\logic\commands\AppointCommand.java
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
                personToEdit.getAddress(), appointment, personToEdit.getProfilePicture(),
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
###### \java\seedu\address\logic\parser\AppointCommandParser.java
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> appointment} into {@code Optional<Appointment>} if {@code appointment} present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Appointment> parseAppointment(Optional<String> appointment) throws IllegalValueException {
        requireNonNull(appointment);
        return appointment.isPresent() ? Optional.of(new Appointment(appointment.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\appointment\Appointment.java
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
###### \java\seedu\address\model\appointment\UniqueAppointmentList.java
``` java
package seedu.address.model.appointment;

/**
 * Class makes sure that there is no clash in appointment
 */
public class UniqueAppointmentList {
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setAppointment(Appointment appointment) {
        this.appointment.set(requireNonNull(appointment));
    }

    @Override
    public ObjectProperty<Appointment> appointmentProperty() {
        return appointment;
    }

    @Override
    public Appointment getAppointment() {
        return appointment.get();
    }

```
