package systemtests;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC_BOUNDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_RONAK;
import static seedu.address.testutil.TypicalPersons.LAKHOTIA;
import static seedu.address.testutil.TypicalPersons.RANDOM;
import static seedu.address.testutil.TypicalPersons.RONAK;
import static seedu.address.testutil.TypicalPersons.SHARMA;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class SearchCommandSystemTest extends AddressBookSystemTest {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    @Test

    public void search() {
        /* Case: search multiple persons in address book, command with leading spaces and trailing spaces
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " "
                + "b/13.10.1997" + "   ";

        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel,
                LAKHOTIA, RANDOM, RONAK, SHARMA); // first names of Lakhotia and Random are "Ronak"
        assertCommandSuccess(command, expectedModel);

        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where person list is displaying the persons we are Searching
         * -> 2 persons found
         */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " " + "b/13.10.1997";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous search command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous search command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: search person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;


        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " " +  "b/13.10.1997";

        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* missing name */
        command = SearchCommand.COMMAND_WORD + " " +  "b/13.10.1997";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* missing date */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* invalid name */
        command = SearchCommand.COMMAND_WORD + " " + INVALID_NAME_DESC + " " + "b/13.10.1997";
        assertCommandFailure(command, String.format(INVALID_DETAILS));

        /* Invalid date */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " "
                + INVALID_DATE_OF_BIRTH_DESC_BOUNDS;
        assertCommandFailure(command, String.format(INVALID_DETAILS));


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
