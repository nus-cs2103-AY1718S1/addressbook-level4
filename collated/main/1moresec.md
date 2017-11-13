# 1moresec
###### /java/seedu/address/logic/parser/FindTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindTaskCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList("picnic", "meet")));
        assertParseSuccess(parser, "picnic / meet", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n picnic \n \t / meet  \t", expectedFindTaskCommand);
    }

}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered person list to show only the first person in the {@code model}'s 3W.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered task list to show only the first task in the {@code model}'s 3W.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTaskBook().getTaskList().get(0);
        final String[] name = task.getName().toString().split("\\s+");
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(Arrays.asList(name[0])));

        assert model.getFilteredTaskList().size() == 1;
    }
```
###### /java/seedu/address/logic/commands/ExportTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportTaskCommand}.
 */
public class ExportTaskCommandTest {
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
        ReadOnlyTask firstTaskToExport = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ReadOnlyTask lastTaskToExport;
        lastTaskToExport = model.getFilteredTaskList().get(lastTaskIndex.getZeroBased());

        final StringBuilder firstBuilder = new StringBuilder();
        firstTaskToExport.getTags().forEach(firstBuilder::append);
        String firstMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + firstTaskToExport.getName(),
                        "d/" + firstTaskToExport.getDescription(),
                        "s/" + firstTaskToExport.getStartDateTime(),
                        "f/" + firstTaskToExport.getEndDateTime(),
                        "t/" + firstBuilder));
        final StringBuilder lastBuilder = new StringBuilder();
        lastTaskToExport.getTags().forEach(lastBuilder::append);
        String lastMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + lastTaskToExport.getName(),
                        "d/" + lastTaskToExport.getDescription(),
                        "s/" + lastTaskToExport.getStartDateTime(),
                        "f/" + lastTaskToExport.getEndDateTime(),
                        "t/" + lastBuilder));

        assertExecutionSuccess(INDEX_FIRST_TASK, firstMessage);
        //assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastTaskIndex, lastMessage);
    }

    @Test
    public void executeInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() {
        showFirstTaskOnly(model);
        ReadOnlyTask taskToExport = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        taskToExport.getTags().forEach(builder::append);
        String expectedMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + taskToExport.getName(),
                        "d/" + taskToExport.getDescription(),
                        "s/" + taskToExport.getStartDateTime(),
                        "f/" + taskToExport.getEndDateTime(),
                        "t/" + builder));

        assertExecutionSuccess(INDEX_FIRST_TASK, expectedMessage);
    }

    @Test
    public void executeInvalidIndexFilteredListFailure() {
        showFirstTaskOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExportTaskCommand exportTaskFirstCommand = new ExportTaskCommand(INDEX_FIRST_TASK);
        ExportTaskCommand exportTaskSecondCommand = new ExportTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(exportTaskFirstCommand.equals(exportTaskFirstCommand));

        // same values -> returns true
        ExportTaskCommand exportTaskFirstCommandCopy = new ExportTaskCommand(INDEX_FIRST_TASK);
        assertTrue(exportTaskFirstCommand.equals(exportTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportTaskFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportTaskFirstCommand.equals(exportTaskSecondCommand));
    }

    /**
     * Executes a {@code ExportCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        ExportTaskCommand exportTaskCommand = prepareCommand(index);

        try {
            CommandResult commandResult = exportTaskCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTaskListRequestEvent lastEvent =
                (JumpToTaskListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Executes a {@code ExportTaskCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ExportTaskCommand exportTaskCommand = prepareCommand(index);

        try {
            exportTaskCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ExportCommand} with parameters {@code index}.
     */
    private ExportTaskCommand prepareCommand(Index index) {
        ExportTaskCommand exportTaskCommand = new ExportTaskCommand(index);
        exportTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportTaskCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ExportCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson firstPersonToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson lastPersonToExport;
        lastPersonToExport = model.getFilteredPersonList().get(lastPersonIndex.getZeroBased());

        final StringBuilder firstBuilder = new StringBuilder();
        firstPersonToExport.getTags().forEach(firstBuilder::append);
        String firstMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + firstPersonToExport.getName(),
                        "p/" + firstPersonToExport.getPhone(),
                        "e/" + firstPersonToExport.getEmail(),
                        "a/" + firstPersonToExport.getAddress(),
                        "r/" + firstPersonToExport.getRemark(),
                        "t/" + firstBuilder));
        final StringBuilder lastBuilder = new StringBuilder();
        lastPersonToExport.getTags().forEach(lastBuilder::append);
        String lastMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + lastPersonToExport.getName(),
                        "p/" + lastPersonToExport.getPhone(),
                        "e/" + lastPersonToExport.getEmail(),
                        "a/" + lastPersonToExport.getAddress(),
                        "r/" + lastPersonToExport.getRemark(),
                        "t/" + lastBuilder));

        assertExecutionSuccess(INDEX_FIRST_PERSON, firstMessage);
        //assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex, lastMessage);
    }

    @Test
    public void executeInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() {
        showFirstPersonOnly(model);
        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        personToExport.getTags().forEach(builder::append);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + personToExport.getName(),
                        "p/" + personToExport.getPhone(),
                        "e/" + personToExport.getEmail(),
                        "a/" + personToExport.getAddress(),
                        "r/" + personToExport.getRemark(),
                        "t/" + builder));

        assertExecutionSuccess(INDEX_FIRST_PERSON, expectedMessage);
    }

    @Test
    public void executeInvalidIndexFilteredListFailure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExportCommand exportFirstCommand = new ExportCommand(INDEX_FIRST_PERSON);
        ExportCommand exportSecondCommand = new ExportCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportCommand exportFirstCommandCopy = new ExportCommand(INDEX_FIRST_PERSON);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    /**
     * Executes a {@code ExportCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        ExportCommand exportCommand = prepareCommand(index);

        try {
            CommandResult commandResult = exportCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ExportCommand exportCommand = prepareCommand(index);

        try {
            exportCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ExportCommand} with parameters {@code index}.
     */
    private ExportCommand prepareCommand(Index index) {
        ExportCommand exportCommand = new ExportCommand(index);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
```
