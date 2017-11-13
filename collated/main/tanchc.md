# tanchc
###### \java\seedu\address\commons\events\ui\JumpToPersonListRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToPersonListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToPersonListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\JumpToTaskListRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTaskListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\TaskPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TaskCard;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\commands\AddTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * Adds a person to the address book.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: "
            + PREFIX_APPOINTMENT + "APPOINTMENT "
            + PREFIX_DATE + "DATE "
            + PREFIX_STARTTIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPOINTMENT + "Meeting "
            + PREFIX_DATE + "25/11/2017 "
            + PREFIX_STARTTIME + "12:00 " + "\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_APPOINTMENT + "Meeting "
            + PREFIX_DATE + "25/11/2017 "
            + PREFIX_STARTTIME + "12:00 ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\FindModuleCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.ModuleContainsKeywordPredicate;

/**
 * Finds and lists all persons in address book whose modules contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindModuleCommand extends Command {
    public static final String COMMAND_WORD = "findmodule";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose modules contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2101" + "\n"
            + "Example: " + COMMAND_ALIAS + " CS2103T";


    private final ModuleContainsKeywordPredicate predicate;

    public FindModuleCommand(ModuleContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && this.predicate.equals(((FindModuleCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
        EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindModuleCommand.COMMAND_WORD:
        case FindModuleCommand.COMMAND_ALIAS:
            return new FindModuleCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT, PREFIX_DATE, PREFIX_STARTTIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPOINTMENT, PREFIX_DATE, PREFIX_STARTTIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Appointment name = ParserUtil.parseAppointment(argMultimap.getValue(PREFIX_APPOINTMENT)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            StartTime startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME)).get();

            ReadOnlyTask task = new Task(name, date, startTime);

            return new AddTaskCommand(task);
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
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_APPOINTMENT = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_STARTTIME = new Prefix("s/");
}
```
###### \java\seedu\address\logic\parser\FindModuleCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindModuleCommand object
 */
public class FindModuleCommandParser implements Parser<FindModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindModuleCommand
     * and returns an FindModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindModuleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindModuleCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindModuleCommand(new ModuleContainsKeywordPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Parses a {@code Optional<String> appointment} into an {@code Optional<Appointment>} if {@code appointment}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Appointment> parseAppointment(Optional<String> appointment) throws IllegalValueException {
        requireNonNull(appointment);
        return appointment.isPresent() ? Optional.of(new Appointment(appointment.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new Date(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        requireNonNull(startTime);
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    private final UniqueTaskList tasks;
```
###### \java\seedu\address\model\AddressBook.java
``` java
        tasks = new UniqueTaskList();
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        this.tasks.setTasks(tasks);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
            setTasks(newData.getTaskList());
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a task to ContactHub.
     * Also checks the new task and updates {@link #tasks} with any new tasks found,
     * and updates the Task objects in the person to point to those in {@link #tasks}.
     *
     */
    public void addTask(ReadOnlyTask t) {
        Task newTask = new Task(t);
        tasks.add(newTask);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     *
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        tasks.setTask(target, editedTask);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the taskList alphabetically
     */
    public void sortTaskListByName() {
        tasks.sortTaskListByName();
    }

    //// module-level operations

    public void addModule(Module m) throws UniqueModuleList.DuplicateModuleException {
        modules.add(m);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + modules.asObservableList().size() +  " modules"
                + tasks.asObservableList().size();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyTask> PREDICATE_SHOW_ALL_TASKS = unused -> true;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given task */
    void addTask(ReadOnlyTask task);
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    private final FilteredList<ReadOnlyTask> filteredTasks;
```
###### \java\seedu\address\model\ModelManager.java
``` java
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(ReadOnlyTask task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();

    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    private String gMapsAddress;
```
###### \java\seedu\address\model\person\Address.java
``` java
        this.gMapsAddress = address.replace("Blk ", "");
```
###### \java\seedu\address\model\person\Address.java
``` java
    public String getGMapsAddress() {
        return gMapsAddress;
    }

```
###### \java\seedu\address\model\person\ModuleContainsKeywordPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.module.Module;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Module} matches any of the keywords given.
 */
public class ModuleContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ModuleContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List<Module> modules = new ArrayList<>(person.getModules());
        for (Module m : modules) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(m.moduleName, keyword))) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleContainsKeywordPredicate // instanceof handles nulls
                && this.keywords.equals(((ModuleContainsKeywordPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();
```
###### \java\seedu\address\model\task\Appointment.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's appointment in the address book.
 */
public class Appointment {

    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String APPOINTMENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String appointment;

    public Appointment(String appointment) throws IllegalValueException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        if (!isValidAppointment(trimmedAppointment)) {
            throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
        }
        this.appointment = trimmedAppointment;
    }

    /**
     * Returns true if a given string is a valid appointment name.
     */
    public static boolean isValidAppointment(String test) {
        return test.matches(APPOINTMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return appointment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.appointment.equals(((Appointment) other).appointment)); // state check
    }

    @Override
    public int hashCode() {
        return appointment.hashCode();
    }

}
```
###### \java\seedu\address\model\task\Date.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents teh date of the Task in the address book.
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date must be in the format of DD/MM/YYYY";

    public static final String DATE_VALIDATION_REGEX =
            "\\d{2}/\\d{2}/\\d{4}";

    public final String value;

    //Validates given Date.
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given String is a valid Date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Date
                && this.value.equals(((Date) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\task\exceptions\TaskNotFoundException.java
``` java
package seedu.address.model.task.exceptions;

/**
 * Signals that the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\task\ReadOnlyTask.java
``` java
package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Appointment> appointmentProperty();
    Appointment getAppointment();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<StartTime> startTimeProperty();
    StartTime getStartTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getAppointment().equals(this.getAppointment()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getStartTime().equals(this.getStartTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAppointment())
                .append(" Date: ")
                .append(getDate())
                .append(" Start Time: ")
                .append(getStartTime());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\task\StartTime.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the start time of the Task in the address book.
 */
public class StartTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "StartTime must be in the format of HH:MM";

    public static final String TIME_VALIDATION_REGEX =
            "\\d{2}:\\d{2}";
    public final String value;

    // Validates given StartTime
    public StartTime(String startTime) throws IllegalValueException {
        requireNonNull(startTime);
        String trimmedTime = startTime.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given String is a valid StartTime.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StartTime
                && this.value.equals(((StartTime) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\task\Task.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Task in ContactHub.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Appointment> appointment;
    private ObjectProperty<Date> date;
    private ObjectProperty<StartTime> startTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Appointment appointment, Date date, StartTime startTime) {
        requireAllNonNull(appointment, date, startTime);
        this.appointment = new SimpleObjectProperty<>(appointment);
        this.date = new SimpleObjectProperty<>(date);
        this.startTime = new SimpleObjectProperty<>(startTime);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getAppointment(), source.getDate(), source.getStartTime());
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public ObjectProperty<Appointment> appointmentProperty() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment.set(requireNonNull(appointment));
    }

    @Override
    public Appointment getAppointment() {
        return appointment.get();
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    @Override
    public ObjectProperty<StartTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime.set(requireNonNull(startTime));
    }

    @Override
    public StartTime getStartTime() {
        return startTime.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyTask> mappedList = EasyBind.map(internalList, (task) -> task);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     */
    public void add(ReadOnlyTask toAdd) {
        requireNonNull(toAdd);
        internalList.add(new Task(toAdd));
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        internalList.set(index, new Task(editedTask));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyTask> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Sorts the list alphabetically
     */
    public void sortTaskListByName() {
        Collections.sort(internalList, Comparator.comparing(firstTask -> firstTask.getAppointment().appointment));
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Appointment("Meeting"), new Date("30/10/2017"), new StartTime("18:00")),
                new Task(new Appointment("Soccer"), new Date("15/12/2017"), new StartTime("09:00")),
                new Task(new Appointment("Birthday"), new Date("30/11/2017"), new StartTime("19:30")),
                new Task(new Appointment("Work"), new Date("01/01/2018"), new StartTime("08:00")),
                new Task(new Appointment("Homework"), new Date("12/01/2018"), new StartTime("23:59")),
                new Task(new Appointment("Exam"), new Date("05/12/2017"), new StartTime("17:00")),
                new Task(new Appointment("Competition"), new Date("25/11/2017"), new StartTime("12:00"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
```
###### \java\seedu\address\storage\XmlAdaptedTask.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getAppointment().appointment;
        date = source.getDate().value;
        startTime = source.getStartTime().value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {

        final Appointment name = new Appointment(this.name);
        final Date date = new Date(this.date);
        final StartTime startTime = new StartTime(this.startTime);
        return new Task(name, date, startTime);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedTask> tasks;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        tasks = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(tk -> {
            try {
                return tk.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    public static final String ADDRESS_PAGE = "PersonBrowserPanel.html";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads the located address page of the user's address.
     */
    private void loadAddressPage(ReadOnlyPerson person) throws IOException {
        URL addressPage = MainApp.class.getResource(FXML_FILE_FOLDER + ADDRESS_PAGE);
        loadPage(addressPage.toExternalForm());
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
        int stopIndex = p.getAddress().getGMapsAddress().indexOf(',');
        String mapAddress;

        if (stopIndex < 0) {
            mapAddress = p.getAddress().getGMapsAddress();
        } else {
            mapAddress = p.getAddress().getGMapsAddress().substring(0, stopIndex);
        }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
        browser.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                WebEngine panel = browser.getEngine();
                panel.executeScript("document.goToLocation(\"" + mapAddress + "\")");
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
        loadAddressPage(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private TaskListPanel taskListPanel;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane taskListPanelPlaceholder;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String[] colors = { "red", "blue", "orange", "brown", "green" };

    private static HashMap<String, String> moduleColors = new HashMap<String, String>();
    private static Random random = new Random();
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static int getRandom() {
        int randNum = random.nextInt(colors.length);
        return randNum;
    }

    private static String getColorForModule(String moduleValue, int randNum) {
        if (!moduleColors.containsKey(moduleValue)) {
            moduleColors.put(moduleValue, colors[randNum]);
        }

        return moduleColors.get(moduleValue);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
            int randNum = getRandom();
            moduleLabel.setStyle("-fx-background-color: " + getColorForModule(module.moduleName, randNum));
            //            if (randNum > 6) {
            //                moduleLabel.setStyle("-fx-text-fill: black");
            //            }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToPersonListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }
```
###### \java\seedu\address\ui\TaskCard.java
``` java
package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.task.ReadOnlyTask;


/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final double GAP = 8;

    private static String[] colors = { "red", "blue", "orange", "brown", "green", "black", "grey", "yellow", "pink" };

    private static HashMap<String, String> moduleColors = new HashMap<String, String>();
    private static Random random = new Random();
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label idtask;
    @FXML
    private Label date;
    @FXML
    private Label startTime;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        idtask.setText(displayedIndex + ". ");
        bindListeners(task);
    }

    private static int getRandom() {
        int randNum = random.nextInt(colors.length);
        return randNum;
    }

    private static String getColorForModule(String modValue, int randNum) {
        if (!moduleColors.containsKey(modValue)) {
            moduleColors.put(modValue, colors[randNum]);
        }

        return moduleColors.get(modValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        taskname.textProperty().bind(Bindings.convert(task.appointmentProperty()));
        date.textProperty().bind(Bindings.convert(task.dateProperty()));
        startTime.textProperty().bind(Bindings.convert(task.startTimeProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return idtask.getText().equals(card.idtask.getText())
                && task.equals(card.task);
    }
}
```
###### \java\seedu\address\ui\TaskListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToTaskListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }
}
```
###### \resources\view\BrowserPanel.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">
  <WebView fx:id="browser" prefWidth="200.0" />
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
```
###### \resources\view\MainWindow.fxml
``` fxml
  <stylesheets>
    <URL value="@DarkTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>

  <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>

  <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4, 0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340.0" prefWidth="340.0" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <VBox fx:id="taskList" minWidth="340.0" prefWidth="340.0" SplitPane.resizableWithParent="false">
        <padding>
          <Insets bottom="10" left="10" right="10" top="10" />
        </padding>
        <StackPane fx:id="taskListPanelPlaceholder" VBox.vgrow="ALWAYS" />
      </VBox>

    <StackPane fx:id="browserPlaceholder" prefWidth="200.0">
```
###### \resources\view\TaskListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <HBox alignment="CENTER_RIGHT" spacing="5">
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="idtask" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="taskname" prefHeight="17.0" prefWidth="129.0" styleClass="cell_big_label" text="\$taskname" />
      </HBox>
      <Label fx:id="date" styleClass="cell_small_label" text="\$date" />
      <Label fx:id="startTime" styleClass="cell_small_label" text="\$startTime" />
    </VBox>
         <StackPane prefHeight="150.0" prefWidth="200.0" />
    </HBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### \resources\view\TaskListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="taskListView" style="-fx-background-color: #383838;" VBox.vgrow="ALWAYS" />
</VBox>
```
