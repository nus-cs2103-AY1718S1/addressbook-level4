package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
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

    //@@author newalter
    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by tag -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "owes*ney";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "95352563";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person when person list is empty -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "owesMoney";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by email -> 2 persons found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "????@example.com cornelia*";
        ModelHelper.setFilteredList(expectedModel, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by email, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "cornelia* ?nn?@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by address -> 3 persons found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS + "AVE";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by tags -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "family owesmoney";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons by phone -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "85355255 95352563";
        ModelHelper.setFilteredList(expectedModel, ALICE);
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

        /* Case: find persons by name and phone and tag -> 3 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alic? "
                + PREFIX_TAG + "owesmoney " + PREFIX_PHONE + "953525??";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag with substring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alic "
                + PREFIX_TAG + "owesmone " + PREFIX_PHONE + "953525";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag with superstring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alicee "
                + PREFIX_TAG + "aowesmoney " + PREFIX_PHONE + "953525633";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address -> 2 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "alice@*.com "
                + PREFIX_ADDRESS + "tokyo";
        ModelHelper.setFilteredList(expectedModel, ALICE, FIONA);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address with substring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "alice@example.co "
                + PREFIX_ADDRESS + "toky";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address with superstring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "calice@example.com "
                + PREFIX_ADDRESS + "tokyo1";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag -> 3 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "b*n "
                + PREFIX_TAG + "FAmily " + PREFIX_PHONE + "*3525??";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons in address book after deleting 1 of them -> 1 person found */
        showAllPersons();
        executeCommand(DeleteCommand.COMMAND_WORD + " 3");
        assert !getModel().getAddressBook().getPersonList().contains(CARL);
        showAllPersons();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name and email, keyword of different case -> 2 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "MeIeR "
                + PREFIX_EMAIL + "lYdIa@eXaMple.Com";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(BENSON.getName().fullName);
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: empty argument -> rejected */
        command = "find";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        command = "FiNd n/Meier";
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
