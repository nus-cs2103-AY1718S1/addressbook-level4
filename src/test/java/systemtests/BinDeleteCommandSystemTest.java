package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_INDEXES;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndexFromBin;
import static seedu.address.testutil.TestUtil.getPersonFromBin;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MUELLER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BinDeleteCommand;
import seedu.address.logic.commands.BinListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author frozventus
public class BinDeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BinDeleteCommand.MESSAGE_USAGE);

    @Test
    public void binDelete() {
        /* ---------------- Performing restore operation while an unfiltered list is being shown ------------------- */

        /* Sets display to binMode */
        String command = BinListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Case: delete the first and last person in the list */
        Model modelBeforeDeletingFirstLast = getModelWithBin();
        Index[] arrayIndex = new Index[2];
        arrayIndex[0] = INDEX_FIRST_PERSON;
        arrayIndex[1] = getLastIndexFromBin(modelBeforeDeletingFirstLast);
        assertCommandSuccess(arrayIndex);

        /* Case: undo deleting the first and last person in the list -> first and last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingFirstLast, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModelWithBin();
        Index lastPersonIndex = getLastIndexFromBin(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        binDeletePerson(modelBeforeDeletingLast, lastPersonIndex);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered bin list, delete index within bounds of recycle bin and bin list -> deleted */
        showPersonsInBinWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModelWithBin().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered bin list, delete index within bounds of recycle bin but out of bounds of bin list
         * -> rejected
         */
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(BinListCommand.COMMAND_WORD);
        showPersonsInBinWithName(KEYWORD_MATCHING_MUELLER);
        int invalidIndex = getModel().getRecycleBin().getPersonList().size();
        command = BinDeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        executeCommand(UndoCommand.COMMAND_WORD);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersonsInBin();
        Model expectedModel = getModelWithBin();
        Index selectedIndex = getLastIndexFromBin(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = BinDeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        ReadOnlyPerson deletedPerson = binDeletePerson(expectedModel, selectedIndex);
        expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRecycleBin().getPersonList().size() + 1);
        command = BinDeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid indexes (not in ascending order) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 2, 1";
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_PERSONS_INDEX);

        /* Case: invalid indexes (identical indexes found) */
        command = BinDeleteCommand.COMMAND_WORD + " 1, 1";
        assertCommandFailure(command, MESSAGE_REPEATED_INDEXES);

        /* Case: invalid index (some valid, some invalid) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 1, " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(BinDeleteCommand.COMMAND_WORD + " abc",
                             MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(BinDeleteCommand.COMMAND_WORD + " 1 abc",
                             MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("BiNDelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Deletes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson binDeletePerson (Model model, Index index) {
        ReadOnlyPerson targetPerson = getPersonFromBin(model, index);
        try {
            model.deleteFromBin(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Restores the person at {@code toDeleteFromBin} by creating a default
     * {@code BinDeleteCommand} using {@code toDeleteFromBin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDeleteFromBin) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson deletedPerson = binDeletePerson(expectedModel, toDeleteFromBin);
        String expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson;

        assertCommandSuccess(
                BinDeleteCommand.COMMAND_WORD + " " + toDeleteFromBin.getOneBased(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Deletes the persons at {@code toDeleteFromBin[]} by creating a default {@code BinDeleteCommand} using
     * {@code toDeleteFromBin[]} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index[] toDeleteFromBin) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson deletedPerson1 = binDeletePerson(expectedModel, toDeleteFromBin[1]);
        ReadOnlyPerson deletedPerson2 = binDeletePerson(expectedModel, toDeleteFromBin[0]);
        String expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson2 + "\n"
                + MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson1;

        assertCommandSuccess(BinDeleteCommand.COMMAND_WORD + " " + toDeleteFromBin[0].getOneBased() + ", "
                                     + toDeleteFromBin[1].getOneBased(), expectedModel, expectedResultMessage);
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
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();
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
        Model expectedModel = getModelWithBin();

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

