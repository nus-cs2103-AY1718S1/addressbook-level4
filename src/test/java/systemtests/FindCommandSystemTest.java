package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PLACES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.FindCommand.COMMAND_WORD_NAME_PREFIX;
import static seedu.address.testutil.TypicalPlaces.BENSON;
import static seedu.address.testutil.TypicalPlaces.CARL;
import static seedu.address.testutil.TypicalPlaces.DANIEL;
import static seedu.address.testutil.TypicalPlaces.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findName() {
        /* Case: find multiple places in address book, command with leading spaces and trailing spaces
         * -> 2 places found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + COMMAND_WORD_NAME_PREFIX
                + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where place list is displaying the places we are finding
         * -> 2 places found
         */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place where place list is not displaying the place we are finding -> 1 place found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords -> 2 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords in reversed order -> 2 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords with 1 repeat -> 2 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 places found
         */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX
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

        /* Case: find same places in address book after deleting 1 of them -> 1 place found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPlaceList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, keyword is same as name but of different case -> 1 place found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, keyword is substring of name -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, name is substring of keyword -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place not in address book -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of place in address book -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of place in address book -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of place in address book -> 0 places found */
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + DANIEL.getWebsite().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of place in address book -> 0 places found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a place is selected -> selected card deselected */
        showAllPlaces();
        selectPlace(Index.fromOneBased(1));
        assert !getPlaceListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find place in empty address book -> 0 places found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPlaceList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_NAME_PREFIX + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd n/Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PLACES_LISTED_OVERVIEW} with the number of places in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PLACES_LISTED_OVERVIEW, expectedModel.getFilteredPlaceList().size());

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
