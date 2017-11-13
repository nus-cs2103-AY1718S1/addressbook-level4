package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_FRIEND;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author chuaweiwen
public class FilterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void filter() {
        /* Case: filter multiple persons with the name in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons we are filtering
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons with the tags in address book,
         * command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        command = "   " + FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + KEYWORD_TAG_FRIEND + "   ";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON); // both Alice and Benson have the tag 'friend'
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the persons we are filtering
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + KEYWORD_TAG_FRIEND;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person where person list is not displaying the person with the name we are finding
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person where person list is not displaying the person with the tag we are finding
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "friend";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 name keywords, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Benson " + PREFIX_NAME + "Meier";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 name keywords in reverse order, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meier " + PREFIX_NAME + "Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 name keywords with one repeat, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Benson " + PREFIX_NAME + "Meier "
                + PREFIX_NAME + "Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 name keywords, only one prefix used
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Benson Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 tag keywords, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "relative" + " " + PREFIX_TAG + "colleague";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 tag keywords in reverse order, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "colleague " + PREFIX_TAG + "relative";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 tag keywords with one repeat, prefix used for each keywords
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "relative " + PREFIX_TAG + "colleague "
                + PREFIX_TAG + "relative";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 2 tag keywords, only one prefix used
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "relative colleague";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 1 name keyword and 1 tag keyword
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER
                + " " + PREFIX_TAG + "friend";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, 1 name keyword and 1 tag keyword, different order
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "friend" + " " + PREFIX_NAME
                + KEYWORD_MATCHING_MEIER;
        ModelHelper.setFilteredList(expectedModel, BENSON);
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

        /* Case: filter same persons with the name in address book after deleting 1 of them -> 1 person found */
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        executeCommand(FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER);
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, name keyword is same as name but of different case
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, tag keyword is same as tag but of different case
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "fRiEnD";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, name keyword is substring of name -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, name is substring of keyword -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Meiers";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person with the name not in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, tag keyword is substring of tag -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "frien";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, tag is substring of tag keyword -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "friendz";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person with the tag not in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "cousin";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiLter Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
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
//@@author
