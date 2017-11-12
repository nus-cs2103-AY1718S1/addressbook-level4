package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandSystemTest extends AddressBookSystemTest {

    //@@author dalessr
    @Test
    public void find() {

        Index firstIndex = new Index(0);

        /* Case: find multiple persons by name in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by name where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons by name in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " I/1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/ " + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, keyword is substring of name -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Mei";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by name not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Mark";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple persons by phone in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 98765432 87652533";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by phone in address book, keyword is substring of phone number -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 8765";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by phone in address book, phone number is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 853552555";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by phone not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 66666666";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple persons by email in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " johnd@example.com  cornelia@example.com";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, keyword is same as email but of different case
         * ->2 person found
         */
        command = FindCommand.COMMAND_WORD + " e/ " + " JoHnD@EXAMPLE.com  corneLIA@example.COM";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, email keyword is substring of the email -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " rne";
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, email address is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " sheinz@example.com";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by email not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " nuscomputing@example.com";
        assertCommandSuccess(command, expectedModel);

        /* Case: find single person by address in address book, 5 keywords -> 1 person found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 311, Clementi Ave 2, #02-25";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by substring of address in address book, 1 keywords -> 3 person found */
        command = FindCommand.COMMAND_WORD + " a/ " + " street";
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by address in address book, keyword is same as address but of different case
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " a/ " + " 10TH StrEEt";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by address in address book, address name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 110th street";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by address not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 311, Clementi Ave 2, #02-31";
        assertCommandSuccess(command, expectedModel);

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author
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
