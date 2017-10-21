package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_KEYWORD_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_REPRESENTATION_DESC_MONDAY;
import static seedu.address.testutil.TypicalAliasTokens.MON;

import org.junit.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;

public class AliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void alias() throws Exception {
        Model model = getModel();
        /* Case : create alias token with keyword and representation, command with leading spaces and trailing spaces
         * -> AliasToken created
         */
        ReadOnlyAliasToken toAdd = MON;
        String command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_KEYWORD_DESC_MONDAY
                + "  " + ALIAS_REPRESENTATION_DESC_MONDAY + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo creating Mon AliasToken to the list -> Mon deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo creating Mon AliasToken to the list -> Mon created again */
        command = RedoCommand.COMMAND_WORD;
        model.addAliasToken(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /*Case : invalid keyword -> rejected */
        assertCommandFailure("alias a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));

        /*Case : invalid representation -> rejected */
        assertCommandFailure("alias k/lol laugh out loud",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyAliasToken)}. Executes {@code command}
     * instead.
     *
     * @see AliasCommandSystemTest #assertCommandSuccess(ReadOnlyAliasToken)
     */
    private void assertCommandSuccess(String command, ReadOnlyAliasToken toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addAliasToken(toAdd);
        } catch (DuplicateTokenKeywordException e) {
            throw new IllegalArgumentException(String.format(toAdd.toString(), "already exists in the model."));
        }
        String expectedResultMessage = String.format(AliasCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyAliasToken)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see AliasCommandSystemTest #assertCommandSuccess(String, ReadOnlyAliasToken)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
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
     *
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

