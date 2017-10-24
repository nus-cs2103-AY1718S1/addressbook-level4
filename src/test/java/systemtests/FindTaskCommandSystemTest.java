package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_TASK_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.BUY_TICKETS;
import static seedu.address.testutil.TypicalTasks.GYM;
import static seedu.address.testutil.TypicalTasks.KEYWORD_MATCHING_FINISH;
import static seedu.address.testutil.TypicalTasks.PERSONAL_PROJECT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.DeleteTaskCommand;
import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.model.Model;

public class FindTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /*change the current command mode to task manager*/
        executeCommand(ChangeModeCommand.COMMAND_WORD + " tm");

        /* Case: find multiple tasks in address book, command with leading spaces and trailing spaces
         * -> 2 tasks found
         */
        String command = "   " + FindTaskCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FINISH + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredTaskList(expectedModel,
            ASSIGNMENT, PERSONAL_PROJECT); // first word of Assignment and Personal_project is "Finish".
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: repeat previous find command where task list is displaying the tasks we are finding
         * -> 2 tasks found
         */
        command = FindTaskCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FINISH;
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: mixed case command word -> 2 tasks found */
        command = "FiNd Finish";
        assertCommandSuccess(command, expectedModel);

        /* Case: find task where task list is not displaying the task we are finding -> 1 task found */
        command = FindTaskCommand.COMMAND_WORD + " Gym";
        ModelHelper.setFilteredTaskList(expectedModel, GYM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 keywords -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " enhancement art";
        ModelHelper.setFilteredTaskList(expectedModel, ASSIGNMENT, PERSONAL_PROJECT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 keywords in reversed order -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " art enhancement";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 keywords with 1 repeat -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " art enhancement art";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 tasks found
         */
        command = FindTaskCommand.COMMAND_WORD + " art enhancement NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same tasks in address book after deleting 1 of them -> 1 task found */
        executeCommand(DeleteTaskCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getTaskList().contains(ASSIGNMENT);
        command = FindTaskCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FINISH;
        expectedModel = getModel();
        ModelHelper.setFilteredTaskList(expectedModel, PERSONAL_PROJECT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task in address book, keyword is same as name but of different case -> 1 task found */
        command = FindTaskCommand.COMMAND_WORD + " FiNiSh";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task in address book, keyword is substring of name -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " fin";
        ModelHelper.setFilteredTaskList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task in address book, name is substring of keyword -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " Finishes";
        ModelHelper.setFilteredTaskList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task not in address book -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " Handsome";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find start date of task in address book -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " " + ASSIGNMENT.getStartDate().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find deadline of task in address book -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " " + ASSIGNMENT.getDeadline().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find while a task is selected -> selected card deselected */
        showAllTasks();
        selectTask(Index.fromOneBased(1));
        assert !getTaskListPanel().getHandleToSelectedCard()
            .getDescription().equals(BUY_TICKETS.getDescription().taskDescription);
        command = FindTaskCommand.COMMAND_WORD + " art";
        ModelHelper.setFilteredTaskList(expectedModel, PERSONAL_PROJECT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardDeselected();

        /* Case: find task in empty address book -> 0 tasks found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        command = FindTaskCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FINISH;
        expectedModel = getModel();
        ModelHelper.setFilteredTaskList(expectedModel, PERSONAL_PROJECT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

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
            MESSAGE_TASK_LISTED_OVERVIEW, expectedModel.getFilteredTaskList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
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
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedTaskCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
