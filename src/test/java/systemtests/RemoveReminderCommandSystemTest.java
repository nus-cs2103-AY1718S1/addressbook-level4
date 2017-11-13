package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX;
import static seedu.address.logic.commands.RemoveReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndexReminder;
import static seedu.address.testutil.TestUtil.getReminder;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
//@@author RonakLakhotia

public class RemoveReminderCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REMINDER_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE);

    @Test
    public void remove() {
        /* ----------------- Performing remove operation while an unfiltered list is being shown
 -------------------- */

        /* Case: delete the first reminder in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + RemoveReminderCommand.COMMAND_WORD + "      "
                + INDEX_SECOND_REMINDER.getOneBased() + "       ";
        ReadOnlyReminder removedReminder = removeReminder(expectedModel, INDEX_SECOND_REMINDER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_REMINDER_SUCCESS, removedReminder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last reminder in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastReminderIndex = getLastIndexReminder(modelBeforeDeletingLast);
        assertCommandSuccess(lastReminderIndex);

        /* Case: undo deleting the last reminder in the list -> last reminder restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last reminder in the list -> last reminder deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeReminder(modelBeforeDeletingLast, lastReminderIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* --------------------------------- Performing invalid remove operation
 ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RemoveReminderCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RemoveReminderCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getReminderList().size() + 1);
        command = RemoveReminderCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RemoveReminderCommand.COMMAND_WORD
                + " abc", MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RemoveReminderCommand.COMMAND_WORD
                + " 1 abc", MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

    }

    /**
     * Removes the {@code ReadOnlyReminder} at the specified {@code index} in {@code model}'s weaver.
     * @return the removed reminder
     */
    private ReadOnlyReminder removeReminder(Model model, Index index) {
        ReadOnlyReminder targetReminder = getReminder(model, index);
        try {
            model.deleteReminder(targetReminder);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("targetReminder is retrieved from model.");
        }
        return targetReminder;
    }
    //@@author
    /**
     * Deletes the reminder at {@code toDelete} by creating a default {@code RemoveCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RemoveReminderCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyReminder deletedReminder = removeReminder(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_REMINDER_SUCCESS, deletedReminder);

        assertCommandSuccess(
                RemoveReminderCommand.COMMAND_WORD + " "
                        + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser
     url
     * and selected card are expected to update accordingly depending on the card at
     {@code expectedSelectedCardIndex}.
     * @see RemoveReminderCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
