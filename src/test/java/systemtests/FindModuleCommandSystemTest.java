// @@author tanchc
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_CS1020;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

public class FindModuleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findModule() {
        /* Case: find multiple modules in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020 + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, BENSON, CARL); // module of Benson is "CS1020"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find module command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module where person list is not displaying the person we are finding -> 2 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords in reversed order -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1231 CS1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 keywords with 1 repeat -> 3 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231 CS1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 matching keywords and 1 non-matching keyword
         * -> 3 persons found
         */
        command = FindModuleCommand.COMMAND_WORD + " CS1020 CS1231 NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find module command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same module in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 2");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, keyword is same as name but of different case -> 1 person found */
        command = FindModuleCommand.COMMAND_WORD + " cs1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, keyword is substring of name -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " cs";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module in address book, name is substring of keyword -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS1010s";
        ModelHelper.setFilteredPersonList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module not in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " CS2020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using name of person in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " " + DANIEL.getName();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using phone number of person in address book -> 0 persons found */
        List<Phone> phones = new ArrayList<>(DANIEL.getPhones());
        command = FindModuleCommand.COMMAND_WORD + " " + phones.get(0);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using address of person in address book -> 0 persons found */
        command = FindModuleCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module using email of person in address book -> 0 persons found */
        List<Email> emails = new ArrayList<>(DANIEL.getEmails());
        command = FindModuleCommand.COMMAND_WORD + " " + emails.get(0);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find module while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindModuleCommand.COMMAND_WORD + " CS1231";
        ModelHelper.setFilteredPersonList(expectedModel, ALICE, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find module in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindModuleCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CS1020;
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
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
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
