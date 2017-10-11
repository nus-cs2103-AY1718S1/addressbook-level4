package systemtests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandSystemTest extends AddressBookSystemTest{

    private static final String MESSAGE_INVALID_DELETE_TAG_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    @Test
    public void deleteTag() throws Exception {
        /* ----------------- Performing delete tag operation while an unfiltered list is being shown -------------------- */

        /* Case: delete existing tag with trailing white space -> deleted */
        Model expectedModel = getModel();
        Tag deletedTag = new Tag("friends");
        String command = " " + DeleteTagCommand.COMMAND_WORD + " " + "friends" + "   " ;
        String expectedResultMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_ALL_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: undo last tag deletion*/
        Model modelAfterDeletingTag = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelAfterDeletingTag, expectedResultMessage);

        /* Case no tag input by user*/
        Model modelBeforeNullInputTag = getModel();
        command = " " + DeleteTagCommand.COMMAND_WORD  + "   " ;
        expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);
        assertCommandSuccess(command, modelBeforeNullInputTag, expectedResultMessage);

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
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
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
}
