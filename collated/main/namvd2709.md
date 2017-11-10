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
###### \java\seedu\address\logic\AppointmentReminder.java
``` java
package seedu.address.logic;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Manages appointment reminder
 */
public class AppointmentReminder {
    public AppointmentReminder(Model model) {
        checkAppointment(model);
    }

    /**
     * Constantly check for appointment
     */
    public void checkAppointment(Model model) {
        Set<Appointment> appointments = model.getAllAppointments();
        showAppointmentMessage(appointments);
        Timer timer = new Timer();
        final int minute = 60000;
        timer.schedule(new TimerTask() {
            public void run() {
                showAppointmentMessage(appointments);
            }
        }, minute, minute);
    }

    /**
     * Show message
     */
    private void showMessage(String message) {
        EventQueue.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message);
        });
    }

    /**
     * Check for any appointment in the next 60 min
     */
    private void showAppointmentMessage(Set<Appointment> appointments) {
        LocalDateTime now = LocalDateTime.now();
        Set<Appointment> toRemove = new HashSet<>();
        for (Iterator<Appointment> appointmentIterator = appointments.iterator(); appointmentIterator.hasNext(); ) {
            Appointment appointment = appointmentIterator.next();
            if (now.until(appointment.getStart(), MINUTES) <= 60) {
                String message = String.format("You have a meeting with %1$s at %2$s",
                        appointment.getPerson().getName(), appointment.toString());
                showMessage(message);
                appointmentIterator.remove();
            }
        }
    }
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
import seedu.address.logic.commands.FilterAllCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UngroupCommand;

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
        SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, UngroupCommand.COMMAND_WORD,
        RemoveTagCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD, FilterAllCommand.COMMAND_WORD
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
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns the appointment reminder */
    AppointmentReminder getAppointmentReminder();
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public AppointmentReminder getAppointmentReminder() {
        return appointmentReminder;
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
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds an appointment to address book.
     */
    public void addAppointment(Appointment a) throws IllegalValueException,
            UniqueAppointmentList.ClashAppointmentException {
        Appointment newAppointment = new Appointment(Appointment.getOriginalAppointment(a.toString()));
        appointments.add(newAppointment);
    }

    /**
     * Removes an appointment
     */
    public void removeAppointment(Appointment a) {
        appointments.remove(a);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
        Appointment oldAppointment = target.getAppointment();
        Appointment newAppointment = editedPerson.getAppointment();
        syncMasterTagListWith(editedPerson);
        syncMasterGroupListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
        appointments.remove(oldAppointment);
        if (!newAppointment.value.equals("")) {
            try {
                appointments.add(newAppointment);
            } catch (UniqueAppointmentList.DuplicateAppointmentException e) {
                e.printStackTrace();
            } catch (UniqueAppointmentList.ClashAppointmentException e) {
                e.printStackTrace();
            }
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public Set<Appointment> getAllAppointments() {
        return persons.getAllAppointments();
    }

```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
package seedu.address.model.appointment;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/MM/yyyy hh:mm duration, the date must be older than today";
    public static final String DATETIME_PATTERN = "dd/MM/uuuu HH:mm";
    public static final String MESSAGE_DURATION_CONSTRAINT = "Duration must be a positive integer in minutes";
    public static final String MESSAGE_DATETIME_CONSTRAINT = "Date time cannot be in the past";
    public static final String MESSAGE_INVALID_DATETIME = "Date or time is invalid";

    public final String value;
    public final LocalDateTime start;
    public final LocalDateTime end;
    private ReadOnlyPerson person;

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
                this.value = startDateTime.format(formatter) + " to " + endDateTime.format(formatter);
                this.start = startDateTime;
                this.end = endDateTime;
            } catch (ArrayIndexOutOfBoundsException iob) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalValueException(MESSAGE_INVALID_DATETIME);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static LocalDateTime getDateTime(String datetime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN)
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(datetime, formatter);
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
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
        return duration.matches("^-?\\d+$")
                    && Integer.parseInt(duration) > 0;
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

    /**
     * Method to help get back original appointment, for passing back to constructor
     */
    public static String getOriginalAppointment(String formattedAppointment) {
        if (!formattedAppointment.equals("")) {
            int splitter = formattedAppointment.indexOf("to");
            String start = formattedAppointment.substring(0, splitter - 1);
            String end = formattedAppointment.substring(splitter + 3);
            LocalDateTime startDateTime = Appointment.getDateTime(start);
            LocalDateTime endDateTime = Appointment.getDateTime(end);
            long duration = startDateTime.until(endDateTime, MINUTES);
            return start + " " + String.valueOf(duration);
        }
        return formattedAppointment;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return end;
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

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 /**
 * A list of appointments that enforces no nulls and no clash time.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    /**
     * Creates a Unique Appointment List.
     * Enforces no nulls.
     */
    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Appointments in this list with those in the argument appointment list.
     */
    public void setAppointments(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an appointment that clashes with the one given.
     */
    public boolean hasClash(Appointment toCheck) {
        requireNonNull(toCheck);
        if (!toCheck.value.equals("")) {
            for (Appointment appointment : internalList) {
                LocalDateTime startA = appointment.getStart();
                LocalDateTime endA = appointment.getEnd();
                LocalDateTime startB = toCheck.getStart();
                LocalDateTime endB = toCheck.getEnd();
                if (startA.isBefore(endB) && endA.isAfter(startB)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a Appointment to the list.
     *
     * @throws DuplicateAppointmentException if the Appointment duplicates of an existing Appointment in the list.
     * @throws ClashAppointmentException if Appointment to add clashes with an existing Appointment in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException, ClashAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        } else if (hasClash(toAdd)) {
            throw new ClashAppointmentException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
    /**
     * Removes the equivalent person from the list.

     */
    public boolean remove(Appointment toRemove) {
        requireNonNull(toRemove);
        final boolean appointmentFound = internalList.remove(toRemove);
        return appointmentFound;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateAppointmentException extends DuplicateDataException {
        protected DuplicateAppointmentException() {
            super("Operation would result in duplicate appointment");
        }
    }

    /**
     * Signals that an operation would have violated the 'no clash' property of appointments.
     */
    public static class ClashAppointmentException extends CommandException {
        public ClashAppointmentException() {
            super("Operation would result in clashing appointments");
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns list of all appointments */
    Set<Appointment> getAllAppointments();

    /** Add appointments */
    void addAppointment(Appointment appointment) throws IllegalValueException,
            UniqueAppointmentList.ClashAppointmentException;

    /** Removes appointment */
    void deleteAppointment(Appointment target);
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAppointment(Appointment appointment) throws IllegalValueException,
                                        UniqueAppointmentList.ClashAppointmentException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteAppointment(Appointment target) {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Set<Appointment> getAllAppointments() {
        return addressBook.getAllAppointments();
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
###### \java\seedu\address\model\person\SortedUniquePersonList.java
``` java
    /**
     * Returns all the appointments in the internal list
     */
    public Set<Appointment> getAllAppointments() {
        Set<Appointment> appointments = new HashSet<>();

        for (Person p: internalList) {
            Appointment appointment = p.getAppointment();
            if (!appointment.value.equals("")) {
                appointment.setPerson(p);
                appointments.add(appointment);
            }
        }
        return appointments;
    }

```
