# tanchc
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void addTask(ReadOnlyTask task) {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/AddTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;


public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void equals() {
        Task meeting = new TaskBuilder().withAppointment("Meeting").build();
        Task soccer = new TaskBuilder().withAppointment("Soccer").build();
        AddTaskCommand addMeetingCommand = new AddTaskCommand(meeting);
        AddTaskCommand addSoccerCommand = new AddTaskCommand(soccer);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddTaskCommand addMeetingCommandCopy = new AddTaskCommand(meeting);
        System.out.println(addMeetingCommand);
        System.out.println(addMeetingCommandCopy);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetingCommand.equals(addSoccerCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given person.
     */
    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(ReadOnlyTask task) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListByName() {
            fail("This method should not be called.");
        }

        @Override
        public void deleteModule(Module module) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) {
            tasksAdded.add(new Task(task));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_APPOINTMENT_MOVIE = "Movie";
    public static final String VALID_APPOINTMENT_EVENT = "Event";
    public static final String VALID_APPOINTMENT_BADMINTON = "Badminton";
    public static final String VALID_DATE_MOVIE = "29/11/2017";
    public static final String VALID_DATE_EVENT = "02/02/2018";
    public static final String VALID_DATE_BADMINTON = "06/01/2018";
    public static final String VALID_START_TIME_MOVIE = "22:00";
    public static final String VALID_START_TIME_EVENT = "10:00";
    public static final String VALID_START_TIME_EVENT_EDIT = "15:00";
    public static final String VALID_START_TIME_BADMINTON = "20:00";
    public static final String VALID_APPOINTMENT_MEETING = "Meeting";
    public static final String VALID_APPOINTMENT_INTERVIEW = "Interview";
    public static final String VALID_DATE_MEETING = "25/11/2017";
    public static final String VALID_DATE_INTERVIEW = "28/10/2017";
    public static final String VALID_START_TIME_MEETING = "12:00";
    public static final String VALID_START_TIME_INTERVIEW = "10:00";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String APPOINTMENT_DESC_MEETING = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_MEETING;
    public static final String APPOINTMENT_DESC_INTERVIEW = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_INTERVIEW;
    public static final String DATE_DESC_MEETING = " " + PREFIX_DATE + VALID_DATE_MEETING;
    public static final String DATE_DESC_INTERVIEW = " " + PREFIX_DATE + VALID_DATE_INTERVIEW;
    public static final String START_TIME_DESC_MEETING = " " + PREFIX_STARTTIME + VALID_START_TIME_MEETING;
    public static final String START_TIME_DESC_INTERVIEW = " " + PREFIX_STARTTIME + VALID_START_TIME_INTERVIEW;
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String APPOINTMENT_DESC_MOVIE = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_MOVIE;
    public static final String APPOINTMENT_DESC_EVENT = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_EVENT;
    public static final String APPOINTMENT_DESC_BADMINTON = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_BADMINTON;
    public static final String DATE_DESC_MOVIE = " " + PREFIX_DATE + VALID_DATE_MOVIE;
    public static final String DATE_DESC_EVENT = " " + PREFIX_DATE + VALID_DATE_EVENT;
    public static final String DATE_DESC_BADMINTON = " " + PREFIX_DATE + VALID_DATE_BADMINTON;
    public static final String START_TIME_DESC_MOVIE = " " + PREFIX_STARTTIME + VALID_START_TIME_MOVIE;
    public static final String START_TIME_DESC_EVENT = " " + PREFIX_STARTTIME + VALID_START_TIME_EVENT;
    public static final String START_TIME_DESC_BADMINTON = " " + PREFIX_STARTTIME + VALID_START_TIME_BADMINTON;

    public static final String INVALID_APPOINTMENT_DESC = " " + PREFIX_APPOINTMENT + "Meetings&"; // '&' not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "01.01.2020"; // '.' not allowed
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_STARTTIME + "1300"; // missing ':' symbol
```
###### /java/seedu/address/logic/commands/SelectCommandTest.java
``` java
        JumpToPersonListRequestEvent lastEvent = (JumpToPersonListRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }
```
###### /java/seedu/address/testutil/TaskBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_APPOINTMENT = "Meeting";
    public static final String DEFAULT_DATE = "25/11/2017";
    public static final String DEFAULT_START_TIME = "12:00";

    private Task task;

    public TaskBuilder() {
        try {
            Appointment defaultAppointment = new Appointment(DEFAULT_APPOINTMENT);
            Date defaultDate = new Date(DEFAULT_DATE);
            StartTime defaultStartTime = new StartTime(DEFAULT_START_TIME);
            this.task = new Task(defaultAppointment, defaultDate, defaultStartTime);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Appointment} of the {@code Task} that we are building.
     */

    public TaskBuilder withAppointment(String appointment) {
        try {
            this.task.setAppointment(new Appointment(appointment));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("appointment is expected to be alphanumeric.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String date) {
        try {
            this.task.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartTime(String startTime) {
        try {
            this.task.setStartTime(new StartTime(startTime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("startTime is expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
```
###### /java/seedu/address/testutil/TaskUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_APPOINTMENT + task.getAppointment().appointment + " ");
        sb.append(PREFIX_DATE + task.getDate().value + " ");
        sb.append(PREFIX_STARTTIME + task.getStartTime().value + " ");
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TypicalTasks.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT_EDIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MOVIE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask MEETING = new TaskBuilder().withAppointment("Meeting")
            .withDate("30/12/2017").withStartTime("18:00").build();

    public static final ReadOnlyTask SOCCER = new TaskBuilder().withAppointment("Soccer")
            .withDate("15/12/2017").withStartTime("09:00").build();

    public static final ReadOnlyTask BIRTHDAY = new TaskBuilder().withAppointment("Birthday")
            .withDate("30/11/2017").withStartTime("19:30").build();

    public static final ReadOnlyTask WORK = new TaskBuilder().withAppointment("Work")
            .withDate("01/01/2018").withStartTime("08:00").build();

    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withAppointment("Homework")
            .withDate("12/01/2018").withStartTime("23:59").build();

    public static final ReadOnlyTask EXAM = new TaskBuilder().withAppointment("Exam")
            .withDate("05/12/2017").withStartTime("17:00").build();

    public static final ReadOnlyTask COMPETITION = new TaskBuilder().withAppointment("Competition")
            .withDate("25/11/2017").withStartTime("12:00").build();

    // Manually added
    public static final ReadOnlyTask TUITION = new TaskBuilder().withAppointment("Tuition")
            .withDate("01/12/2017").withStartTime("15:00").build();

    public static final ReadOnlyTask CHECKUP = new TaskBuilder().withAppointment("Checkup")
            .withDate("12/02/2018").withStartTime("14:00").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask MOVIE = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE)
            .withDate(VALID_DATE_MOVIE).withStartTime(VALID_START_TIME_MOVIE).build();
    public static final ReadOnlyTask EVENT = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT)
            .withDate(VALID_DATE_EVENT).withStartTime(VALID_START_TIME_EVENT).build();
    public static final ReadOnlyTask EVENT_EDITED = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT)
            .withDate(VALID_DATE_EVENT).withStartTime(VALID_START_TIME_EVENT_EDIT).build();
    public static final ReadOnlyTask BADMINTON = new TaskBuilder().withAppointment(VALID_APPOINTMENT_BADMINTON)
            .withDate(VALID_DATE_BADMINTON).withStartTime(VALID_START_TIME_BADMINTON).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final String KEYWORD_MATCHING_OWESMONEY = "owesMoney"; // A keyword that matches friends

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBookTasks() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyTask task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(MEETING, SOCCER, BIRTHDAY, WORK, HOMEWORK, EXAM, COMPETITION));
    }
}
```
###### /java/seedu/address/ui/PersonListPanelTest.java
``` java
    private static final JumpToPersonListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToPersonListRequestEvent(INDEX_SECOND_PERSON);
```
###### /java/systemtests/AddTaskCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MOVIE;
import static seedu.address.testutil.TypicalTasks.MOVIE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a task without mods to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyTask toAdd = MOVIE;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + APPOINTMENT_DESC_MOVIE + "  " + DATE_DESC_MOVIE
                + "  " + START_TIME_DESC_MOVIE + " ";
        assertTaskCommandSuccess(command, toAdd);

        /* Case: undo adding Meeting to the list -> Meeting deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertTaskCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Meeting to the list -> Meeting added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertTaskCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a task with all fields same as another task in the address book except appointment -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT).withDate(VALID_DATE_MOVIE)
                .withStartTime(VALID_START_TIME_MOVIE).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_EVENT + DATE_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except date -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE).withDate(VALID_DATE_EVENT)
                .withStartTime(VALID_START_TIME_MOVIE).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_EVENT + START_TIME_DESC_MOVIE;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE).withDate(VALID_DATE_MOVIE)
                .withStartTime(VALID_START_TIME_EVENT).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE + START_TIME_DESC_EVENT;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: missing appointment -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DATE_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing startTime -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addtasks " + TaskUtil.getTaskDetails(toAdd);
        assertTaskCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + INVALID_DATE_DESC + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid startTime -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE + INVALID_START_TIME_DESC;
        assertTaskCommandFailure(command, StartTime.MESSAGE_TIME_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing {@code AddCommand}
     * with the details of {@code toAdd}, and the model related components equal to the current model added with
     * {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertTaskCommandSuccess(ReadOnlyTask toAdd) {
        assertTaskCommandSuccess(TaskUtil.getAddTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertTaskCommandSuccess(ReadOnlyTask)}. Executes {@code command}
     * instead.
     * @see AddTaskCommandSystemTest#assertTaskCommandSuccess(ReadOnlyTask)
     */
    private void assertTaskCommandSuccess(String command, ReadOnlyTask toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTask(toAdd);

        String expectedResultMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, toAdd);

        assertTaskCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertTaskCommandSuccess(String, ReadOnlyTask)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddTaskCommandSystemTest#assertTaskCommandSuccess(String, ReadOnlyTask)
     */
    private void assertTaskCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertTaskCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/EditTaskCommandSystemTest.java
``` java
        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + DATE_DESC_MEETING;
        ReadOnlyTask taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withDate(VALID_DATE_MEETING).build();
        assertCommandSuccess(command, index, editedTask);
```
###### /java/systemtests/FindModuleCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_CS1020;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

public class FindModuleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findModule() {
        /* Case: find multiple modules in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020 + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, CARL); // module of Benson is "CS1020"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find module command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module where person list is not displaying the person we are finding -> 2 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords in reversed order -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1231 CS1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords with 1 repeat -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231 CS1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 matching keywords and 1 non-matching keyword
         * -> 3 persons found
         */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231 NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find module command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same module in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 2");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, keyword is same as name but of different case -> 1 person found */
        command = FindModuleCommand.COMMAND_WORD + " cs1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, keyword is substring of name -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " cs";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, name is substring of keyword -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1010s";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module not in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS2020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using name of person in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " " + DANIEL.getName();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using phone number of person in address book -> 0 persons found */
        List<Phone> phones = new ArrayList<>(DANIEL.getPhones());
        command = FindModuleCommand.COMMAND_WORD + " " + phones.get(0);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using address of person in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using email of person in address book -> 0 persons found */
        List<Email> emails = new ArrayList<>(DANIEL.getEmails());
        command = FindModuleCommand.COMMAND_WORD + " " + emails.get(0);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find module while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindModuleCommand.COMMAND_WORD + " CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find module in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        // assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        // assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
