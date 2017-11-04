//@@author inGall
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PHONES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindPhoneCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findPhone() {
        Model expectedModel = getModel();

        /* Case: find phone where phone list is not displaying the phone we are finding -> 1 phone found */
        String command = FindPhoneCommand.COMMAND_WORD + " 95352563";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 98765432 87652533";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords in reversed order -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords with 1 repeat -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432 87652533";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 phones found
         */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432 NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find phone in address book, keyword is substring of name -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 9876";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in address book, name is substring of keyword -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 987654321";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone not in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 82345678";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + BENSON.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 phones found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindPhoneCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in empty address book -> 0 phones found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindPhoneCommand.COMMAND_WORD + " 87652533";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdpHONE 87652533";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PHONES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PHONES_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
