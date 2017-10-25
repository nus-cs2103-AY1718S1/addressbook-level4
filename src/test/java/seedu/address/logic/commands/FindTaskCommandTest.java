package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.BUY_TICKETS;
import static seedu.address.testutil.TypicalTasks.GYM;
import static seedu.address.testutil.TypicalTasks.PERSONAL_PROJECT;
import static seedu.address.testutil.TypicalTasks.QUIZ;
import static seedu.address.testutil.TypicalTasks.SUBMISSION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TaskContainsKeywordsPredicate firstPredicate =
            new TaskContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskContainsKeywordsPredicate secondPredicate =
            new TaskContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTaskCommand findFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findSecondCommand = new FindTaskCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTaskCommand findFirstCommandCopy = new FindTaskCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 0);
        FindTaskCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 4);
        FindTaskCommand command = prepareCommand("Finish gym online");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ASSIGNMENT, QUIZ, GYM, PERSONAL_PROJECT));
    }

    @Test
    public void executeMultipleDeadlinesMultipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 2);
        FindTaskCommand command = prepareCommand("28-10-2017 01-11-2017");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(QUIZ, BUY_TICKETS));
    }

    @Test
    public void executeMultipleKeywordTypeMultipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 3);
        FindTaskCommand command = prepareCommand("code 28-10-2017 30-11-2017");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ASSIGNMENT, QUIZ, SUBMISSION));
    }

    /**
     * Parses {@code userInput} into a {@code FindTaskCommand}.
     */
    private FindTaskCommand prepareCommand(String userInput) {
        FindTaskCommand command =
            new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyTask>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTaskCommand command,
                                      String expectedMessage, List<ReadOnlyTask> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
