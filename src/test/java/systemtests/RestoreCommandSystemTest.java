package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_INDEXES;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndexFromBin;
import static seedu.address.testutil.TestUtil.getPersonFromBin;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MUELLER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BinListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author frozventus
public class RestoreCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_RESTORE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE);

    @Test
    public void restore() {
        /* ---------------- Performing restore operation while an unfiltered list is being shown ------------------- */

        /* Sets display to binMode */
        String command = BinListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Case: restore the first and last person in the list */
        Model modelBeforeRestoringFirstLast = getModelWithBin();
        Index[] arrayIndex = new Index[2];
        arrayIndex[0] = INDEX_FIRST_PERSON;
        arrayIndex[1] = getLastIndexFromBin(modelBeforeRestoringFirstLast);
        assertCommandSuccess(arrayIndex);

        /* Case: undo restoring the first and last person in the list -> first and last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeRestoringFirstLast, expectedResultMessage);

        /* Case: restore the last person in the list -> restored */
        Model modelBeforeRestoringLast = getModelWithBin();
        Index lastPersonIndex = getLastIndexFromBin(modelBeforeRestoringLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo restoring the last person in the list -> last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeRestoringLast, expectedResultMessage);

        /* Case: redo restoring the last person in the list -> last person restored again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        restorePerson(modelBeforeRestoringLast, lastPersonIndex);
        assertCommandSuccess(command, modelBeforeRestoringLast, expectedResultMessage);

        /* ----------------- Performing restore operation while a filtered list is being shown ---------------------- */

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
        command = RestoreCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        executeCommand(UndoCommand.COMMAND_WORD);


        /* -------------------- Performing restore operation while a person card is selected ------------------------ */

        /* Case: restore the selected person -> person list panel selects the person before restoring the person */
        showAllPersonsInBin();
        Model expectedModel = getModelWithBin();
        Index selectedIndex = getLastIndexFromBin(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = RestoreCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        ReadOnlyPerson restoredPerson = restorePerson(expectedModel, selectedIndex);
        expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* -------------------------------- Performing invalid restore operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRecycleBin().getPersonList().size() + 1);
        command = RestoreCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid indexes (not in ascending order) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 2, 1";
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_PERSONS_INDEX);

        /* Case: invalid indexes (identical indexes found) */
        command = RestoreCommand.COMMAND_WORD + " 1, 1";
        assertCommandFailure(command, MESSAGE_REPEATED_INDEXES);

        /* Case: invalid index (some valid, some invalid) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 1, " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " abc",
                             MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " 1 abc",
                             MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ReStOrE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Restores the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson restorePerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getPersonFromBin(model, index);
        try {
            model.restorePerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException dpe) {
            assert false : "Duplicate person will not be added to AddressBook.";
        }
        return targetPerson;
    }

    /**
     * Restores the person at {@code toRestore} by creating a default {@code RestoreCommand} using {@code toRestore} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toRestore) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson restoredPerson = restorePerson(expectedModel, toRestore);
        String expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson;

        assertCommandSuccess(
                RestoreCommand.COMMAND_WORD + " " + toRestore.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Restores the persons at {@code toRestore[]} by creating a default {@code RestoreCommand} using
     * {@code toRestore[]} and performs the same verification as {@code assertCommandSuccess (String, Model, String)}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index[] toRestore) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson restoredPerson2 = restorePerson(expectedModel, toRestore[1]);
        ReadOnlyPerson restoredPerson1 = restorePerson(expectedModel, toRestore[0]);
        String expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson1 + "\n"
                + MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson2;

        assertCommandSuccess(RestoreCommand.COMMAND_WORD + " " + toRestore[0].getOneBased() + ", "
                                     + toRestore[1].getOneBased(), expectedModel, expectedResultMessage);
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
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
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

