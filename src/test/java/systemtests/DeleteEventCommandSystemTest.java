package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.address.testutil.TestUtil.getEvent;
import static seedu.address.testutil.TestUtil.getEventLastIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.EventNotFoundException;

//@@author royceljh
public class DeleteEventCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

    @Test
    public void delete() {

        /* Case: delete the first event in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteEventCommand.COMMAND_WORD + "      " + INDEX_FIRST_EVENT.getOneBased()
                + "       ";
        ReadOnlyEvent deletedEvent = removeEvent(expectedModel, INDEX_FIRST_EVENT);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last event in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastEventIndex = getEventLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastEventIndex);

        /* Case: undo deleting the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last event in the list -> last event deleted */
        command = RedoCommand.COMMAND_WORD;
        removeEvent(modelBeforeDeletingLast, lastEventIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteEventCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteEventCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getEventList().size() + 1);
        command = DeleteEventCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteEventCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteEventCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyEvent} at the specified {@code index} in {@code model}'s address book.
     * @return the removed event
     */
    private ReadOnlyEvent removeEvent(Model model, Index index) {
        ReadOnlyEvent targetEvent = getEvent(model, index);
        try {
            model.deleteEvent(targetEvent);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("targetEvent is retrieved from model.");
        }
        return targetEvent;
    }

    /**
     * Deletes the event at {@code toDelete} by creating a default {@code DeleteEventCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyEvent deletedEvent = removeEvent(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent);

        assertCommandSuccess(
                DeleteEventCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
