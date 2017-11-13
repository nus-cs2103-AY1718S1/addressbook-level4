//@@author srishag
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.model.Model;

public class SendEmailCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SEND_EMAIL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE);
    private String command;

    @Test
    public void send() {

        /* ----------------------- Perform send email operation while a filtered list is being shown --------------- */

        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = SendEmailCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_EMAIL_SUBJECT_DESC
                + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid send operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " 0" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid index (-1) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " -1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = SendEmailCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased()
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " " + "abc"
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid arguments (extra argument) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " " + "1 abc"
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: mixed case command word -> rejected */
        command = "sEnD" + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
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

