//@@author AceCentury

package systemtests;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_EMPTY_ADDRESS_BOOK;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_EXPORT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Model;

public class ExportCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void exportCommand() {
        /*----------------- Performing export operation while an unfiltered list is being shown --------------------*/
        assertCommandSuccess();
        Index firstIndex = INDEX_FIRST_PERSON;
        selectPerson(firstIndex);
        assertCommandSuccess();

        /*----------------- Performing export operation while a filtered list is being shown --------------------*/

        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess();

        /*----------------- Performing export operation with an empty Address Book --------------------*/
        executeCommand(ClearCommand.COMMAND_WORD);
        assertCommandFailure(ExportCommand.COMMAND_WORD, MESSAGE_EMPTY_ADDRESS_BOOK);
    }

    /**
     * Exports current Address Book Data by creating a {@code ExportCommand} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see ExportCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess() {
        Model expectedModel = getModel();
        String expectedResultMessage = MESSAGE_EXPORT_SUCCESS;

        assertCommandSuccess(
                ExportCommand.COMMAND_WORD, expectedModel, expectedResultMessage);
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
     * @see DeleteByNameCommandSystemTest#assertCommandSuccess(String, Model, String)
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
        assertStatusBarUnchanged();
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
