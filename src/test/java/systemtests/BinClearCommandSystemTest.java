package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BinClearCommand;
import seedu.address.logic.commands.BinListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;

//@@author frozventus
public class BinClearCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void clear() {
        executeCommand(BinListCommand.COMMAND_WORD);
        final Model defaultModel = getModelWithBin();

        /* Case: clear non-empty recycle bin, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + BinClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardDeselected();

        /* Case: undo clearing recycle bin -> original recycle bin restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        Model binClearedModel = getModelWithBin();
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardDeselected();

        /* Case: redo clearing recyclebin -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: selects first card in person list and clears recycle bin -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original recycle bin
        selectPerson(Index.fromOneBased(1));
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: filters the recycle bin before clearing -> entire recycle bin cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: clear empty recycle bin -> cleared */
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("bINClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code BinClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        Model binClearedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        binClearedModel.setBinDisplay();
        assertCommandSuccess(command, BinClearCommand.MESSAGE_SUCCESS, binClearedModel);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see BinClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
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
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModelWithBin();

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
