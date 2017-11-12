# ShaocongDong
###### /java/seedu/address/logic/parser/SelectTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.SelectTaskCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectTaskCommandParserTest {

    private SelectTaskCommandParser parser = new SelectTaskCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/AddTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOTPOT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTaskHotpot = new TaskBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withStart(VALID_START_HOTPOT)
                .withEnd(VALID_END_HOTPOT).withTags(VALID_TAG_HOTPOT).build();

        Task expectedTaskDemo = new TaskBuilder().withName(VALID_NAME_DEMO)
                .withDescription(VALID_DESCRIPTION_DEMO).withStart(VALID_START_DEMO)
                .withEnd(VALID_END_DEMO).withTags(VALID_TAG_DEMO).build();

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_HOTPOT
                + DESC_DESC_HOTPOT + START_DESC_HOTPOT
                + END_DESC_HOTPOT + TAG_DESC_HOTPOT, new AddTaskCommand(expectedTaskHotpot));

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, new AddTaskCommand(expectedTaskDemo));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_NAME_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + VALID_DESCRIPTION_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing start prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + VALID_START_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing end prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + VALID_END_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing tag prefix -> to be un commented after date field being implemented
        //assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO +
        //        DESC_DESC_DEMO + START_DESC_DEMO
        //        + END_DESC_DEMO + VALID_TAG_DEMO, expectedMessage);

        // missing all prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_NAME_DEMO
                + VALID_DESCRIPTION_DEMO + VALID_START_DEMO
                + VALID_END_DEMO + VALID_TAG_DEMO, expectedMessage);

    }

}
```
###### /java/seedu/address/logic/commands/SetPriorityCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SetPriorityCommandTest}.
 */
public class SetPriorityCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionSuccess(INDEX_FIRST_TASK, 1);
        assertExecutionSuccess(INDEX_THIRD_TASK, 1);
        assertExecutionSuccess(lastTaskIndex, 1);
    }

    @Test
    public void executeInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, 1, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidPriorityValueFailure() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionFailure(lastTaskIndex, 100, SetPriorityCommand.MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE);

    }

    @Test
    public void equals() {
        SetPriorityCommand setPriorityFirstCommand = new SetPriorityCommand(INDEX_FIRST_TASK, 1);
        SetPriorityCommand setPrioritySecondCommand = new SetPriorityCommand(INDEX_SECOND_TASK, 2);

        // same object
        assertTrue(setPriorityFirstCommand.equals(setPriorityFirstCommand));

        // same values -> returns true
        SetPriorityCommand setPriorityFirstCommandCopy = new SetPriorityCommand(INDEX_FIRST_TASK, 1);
        assertTrue(setPriorityFirstCommand.equals(setPriorityFirstCommandCopy));

        // different types -> returns false
        assertFalse(setPriorityFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setPriorityFirstCommand == null);

        // different task -> returns false
        assertFalse(setPriorityFirstCommand.equals(setPrioritySecondCommand));
    }

    /**
     * Executes a {@code SetPriorityCommand} with the given {@code index}, and checks that
     * the updated task priority is properly reflected.
     */
    private void assertExecutionSuccess(Index index, Integer value) {
        SetPriorityCommand setPriorityCommand = prepareCommand(index, value);
        int indexInteger = index.getZeroBased();

        try {
            CommandResult commandResult = setPriorityCommand.execute();
            assertEquals(String.format(SetPriorityCommand.MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS,
                    getTypicalTaskbook().getTaskList().get(indexInteger).getName()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertEquals(value, model.getTaskBook().getTaskList().get(indexInteger).getPriority());
    }

    /**
     * Assert execution failure by default, a wrong execution command inputted.
     * @param index , the index of the task
     * @param value , the new priority
     * @param expectedMessage , the expected message String
     */
    private void assertExecutionFailure(Index index, Integer value, String expectedMessage) {
        SetPriorityCommand setPriorityCommand = prepareCommand(index, value);

        try {
            setPriorityCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SetPriorityCommand} with parameters {@code index}.
     */
    private SetPriorityCommand prepareCommand(Index index, Integer value) {
        SetPriorityCommand setPriorityCommand = new SetPriorityCommand(index, value);
        setPriorityCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setPriorityCommand;
    }
}
```
###### /java/seedu/address/logic/commands/AddTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TypicalTasks;

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
        ReadOnlyTask validTask = TypicalTasks.getTypicalTasks().get(0);

        //CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        //assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);

        modelStub.addTask(validTask);
        assertEquals(1, Arrays.asList(validTask).size());
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        ReadOnlyTask validTask = TypicalTasks.getTypicalTasks().get(0);

        thrown.expect(DuplicateTaskException.class);
        //thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        modelStub.addTask(validTask);
    }

    @Test
    public void equals() {

    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddTaskCommand getAddCommandForTask(Task task, Model model) {
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
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTaskBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            fail("This method should not be called");
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
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTaskPriority(ReadOnlyTask target, Integer value) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void markTask(ReadOnlyTask task) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public SortedList<ReadOnlyTask> getSortedTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void taskByEnd() {
            fail("This method should not be called.");
        }

        @Override
        public void taskByPriority() {
            fail("This method should not be called.");
        }

    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return new TaskBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            tasksAdded.add(new Task(task));
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return new TaskBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/ClearTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;

public class ClearTaskCommandTest {

    @Test
    public void executeEmptyAddressBookSuccess() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeNonEmptyTaskBookSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TaskBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearTaskCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearTaskCommand prepareCommand(Model model) {
        ClearTaskCommand command = new ClearTaskCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/SelectTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectTaskCommand}.
 */
public class SelectTaskCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionSuccess(INDEX_FIRST_TASK);
        assertExecutionSuccess(INDEX_THIRD_TASK);
        assertExecutionSuccess(lastTaskIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectTaskCommand selectFirstCommand = new SelectTaskCommand(INDEX_FIRST_TASK);
        SelectTaskCommand selectSecondCommand = new SelectTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectTaskCommand selectFirstCommandCopy = new SelectTaskCommand(INDEX_FIRST_TASK);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectTaskCommand} with the given {@code index}, and checks that
     * {@code JumpToTaskListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectTaskCommand selectTaskCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectTaskCommand.execute();
            assertEquals(String.format(SelectTaskCommand.MESSAGE_SELECT_TASK_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTaskListRequestEvent lastEvent =
                (JumpToTaskListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectTaskCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectTaskCommand selectTaskCommand = prepareCommand(index);

        try {
            selectTaskCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectTaskCommand} with parameters {@code index}.
     */
    private SelectTaskCommand prepareCommand(Index index) {
        SelectTaskCommand selectTaskCommand = new SelectTaskCommand(index);
        selectTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectTaskCommand;
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return new TaskBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/TaskByPriorityCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TaskByPriorityCommand.
 */
public class TaskByPriorityCommandTest {

    private Model model;
    private TaskByPriorityCommand taskByPriorityCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        taskByPriorityCommand = new TaskByPriorityCommand();
        taskByPriorityCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute() {
        Model expectedModel;
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        expectedModel.taskByPriority();
        assertCommandSuccess(taskByPriorityCommand, model, taskByPriorityCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);


        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskBook(), new UserPrefs());

        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(Index index) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(index);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
```
###### /java/seedu/address/testutil/TaskBuilder.java
``` java
    public TaskBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            DateTime defaultStart = new DateTime(DEFAULT_START_DATE_TIME);
            DateTime defaultEnd = new DateTime(DEFAULT_END_DATE_TIME);
            Integer defaultPriority = DEFAULT_PRIORITY;
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.task = new Task(defaultName, defaultDescription, defaultStart, defaultEnd,
                    defaultTags, false, defaultPriority);
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
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        try {
            this.task.setName(new Name(name));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("The name is invalid");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        try {
            this.task.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        try {
            this.task.setDescription(new Description(description));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("The description is invalid");
        }
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code Task} that we are building.
     */
    public TaskBuilder withStart(String start) {
        try {
            this.task.setStartDateTime(new DateTime(start));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Start date time is invalid");
        }
        return this;
    }

    /**
     * Sets the {@code End} of the {@code Task} that we are building.
     */
    public TaskBuilder withEnd(String end) {
        try {
            this.task.setEndDateTime(new DateTime(end));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("End date time is invalid");
        }
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */

    public Task build() {
        return this.task;
    }

}
```
###### /java/seedu/address/testutil/TypicalTasks.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask PICNIC = new TaskBuilder().withName("picnic")
            .withDescription("Have a good time with my best friends")
            .withStart("20-05-2018 11:00pm")
            .withEnd("20-05-2018 12:00pm").withTags("Friends", "Fun").build();

    public static final ReadOnlyTask MEETING = new TaskBuilder().withName("meeting")
            .withDescription("Have a CS2101 group meeting for oral presentation 2")
            .withStart("20-05-2017 11:00pm")
            .withEnd("20-05-2017 12:00pm").withTags("Study").build();

    public static final ReadOnlyTask EXAM = new TaskBuilder().withName("CS2103 exam")
            .withDescription("Have a final exam for CS2103T")
            .withStart("20-05-2016 11:00pm")
            .withEnd("20-05-2016 12:00pm").withTags("Study").build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * get a typical task book, keep it empty for now
     * @return
     */
    public static TaskBook getTypicalTaskbook() {
        TaskBook tb = new TaskBook();
        for (ReadOnlyTask rot : getTypicalTasks()) {
            try {
                tb.addTask(rot);
            } catch (DuplicateTaskException e) {
                assert false : "There are duplicate tasks in this Taskbook";
            }
        }
        return tb;
    }

    /**
     * return the typical tasks inside this application - examples for users
     * @return the typical tasks as an arrayList
     */
    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(PICNIC, MEETING, EXAM));
    }


}
```
