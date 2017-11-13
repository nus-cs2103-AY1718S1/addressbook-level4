package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEN;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BinListCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " "
                + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " "
                + "Daniel Benson NonMatchingKeyWord";
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

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Mei";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_PHONE + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book, keyword is substring of number -> 1 person found */
        String substring = DANIEL.getPhone().value.substring(0, DANIEL.getPhone().value.length() / 2);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_PHONE + " " + substring;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book, number is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_PHONE + " " + DANIEL.getPhone().value + "123";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ADDRESS + " " + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book, keyword is substring of address -> 1 person found */
        substring = DANIEL.getAddress().value.substring(0, DANIEL.getAddress().value.length() / 2);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ADDRESS + " " + substring;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book, address is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ADDRESS + " "
                + DANIEL.getAddress().value + "123";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_EMAIL + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book, keyword is substring of email -> 1 person found */
        substring = DANIEL.getEmail().value.substring(0, DANIEL.getEmail().value.length() / 2);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_EMAIL + " " + substring;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book, email is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_EMAIL + " " + DANIEL.getEmail().value + "123";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find birthday month of person in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_BIRTHDAY + " "
                + DANIEL.getBirthday().getMonth();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find birthday month of person in address book, invalid month provided -> rejected */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_BIRTHDAY + " " + "13";
        expectedResultMessage = FindCommand.MESSAGE_INVALID_BIRTHDAY_MONTH;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find tags of person in address book -> 6 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_TAG + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE, DANIEL, CARL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book, keyword is substring of tag -> 6 persons found */
        substring = tags.get(0).tagName.substring(0, tags.get(0).tagName.length() / 2);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_TAG + " " + substring;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book, tag is substring of keyword -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_TAG + " " + tags.get(0).tagName + "123";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find organisation of person in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ORGANISATION
                + " " + DANIEL.getOrganisation().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find organisation of person in address book, keyword is substring of tag -> 6 persons found */
        substring = DANIEL.getOrganisation().value.substring(0, DANIEL.getOrganisation().value.length() / 2);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ORGANISATION + " " + substring;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find organisation of person in address book, organisation is substring of keyword -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_ORGANISATION + " "
                + DANIEL.getOrganisation().value + "123";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd" + " " + FindCommand.KEYWORD_NAME + " " + "Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: find without main keyword -> rejected */
        command = "find" + " " + KEYWORD_MATCHING_MEIER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        /* Case: switch to bin display and filter -> switch to bin display -> 1 person found */
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(BinListCommand.COMMAND_WORD);
        expectedModel = getModel();
        expectedModel.setBinDisplay();
        ModelHelper.setFilteredList(expectedModel, KEN);
        command = "find" + " " + FindCommand.KEYWORD_NAME + " " + "Ken";
        assertCommandSuccessWithBin(command, expectedModel);

        /* Case: switch back to list mode and find -> 0 persons found*/
        executeCommand(ListCommand.COMMAND_WORD);
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        expectedModel.setListDisplay();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: switch to bin display and filter -> switch to bin display -> 0 person found */
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(BinListCommand.COMMAND_WORD);
        expectedModel = getModel();
        expectedModel.setBinDisplay();
        ModelHelper.setFilteredList(expectedModel);
        command = "find" + " " + FindCommand.KEYWORD_NAME + " " + "Pauline";
        assertCommandSuccessWithBin(command, expectedModel);

        /* Case: switch back to bin mode and find -> 0 persons found*/
        executeCommand(BinListCommand.COMMAND_WORD);
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        expectedModel.setBinDisplay();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessWithBin(command, expectedModel);
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
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessWithBin(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
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
