package systemtests;
//@@author yangminxingnus
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;


public class RemarkCommandSystemTest extends AddressBookSystemTest {


    private static final String MESSAGE_INVALID_REMARK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    @Test
    public void addRemarkTests() {
        /* ----------------- Performing photo operation while an unfiltered list is being shown -------------------- */

        /* Case: Add photo to the first person in the list, command with leading spaces and
          trailing spaces -> deleted */

        String remark = "CS2101/SEC/1";

        Model expectedModel = getModel();
        String command = RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_REMARK + remark;

        ReadOnlyPerson person = getTargetPerson(expectedModel, INDEX_FIRST_PERSON, remark);
        String expectedResultMessage = String.format(MESSAGE_ADD_REMARK_SUCCESS, person);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* --------------------------------- Performing invalid remark operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " -1 " + PREFIX_REMARK + remark;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing fileds -> rejected */
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_REMARK + "//";
        assertCommandFailure(command, MESSAGE_INVALID_REMARK_COMMAND_FORMAT);
    }

    /**
     * Adds remark to the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the person with the remark added
     */
    private ReadOnlyPerson getTargetPerson(Model model, Index index, String remark) {
        ReadOnlyPerson targetPerson = getPerson(model, index);

        model.addRemarkPerson(targetPerson, remark, index);

        return targetPerson;
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

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see PhotoCommandSystemTest#assertCommandSuccess(String, Model, String)
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
//@@author
