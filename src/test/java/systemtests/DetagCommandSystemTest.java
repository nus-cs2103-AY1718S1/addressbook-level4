package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DetagCommand.MESSAGE_DETAG_PERSONS_SUCCESS;
import static seedu.address.logic.commands.DetagCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.DetagCommand.MESSAGE_MISSING_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DetagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

public class DetagCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DetagCommand.MESSAGE_USAGE);

    @Test
    public void detag() throws CommandException {
        /* ----------------- Performing detag operation while an unfiltered list is being shown -------------------- */

        /* Case: detag first tag of the first person in the list, command with leading spaces and trailing spaces
         *  -> detag 1 tag of first person
         *  */
        Model expectedModel = getModel();
        Tag deletedPersonTag = removeTag(expectedModel, INDEX_FIRST_PERSON);
        String command = "     " + DetagCommand.COMMAND_WORD + "   " + INDEX_FIRST_PERSON.getOneBased() + "  "
                + PREFIX_TAG + deletedPersonTag.tagName;
        String expectedResultMessage = String.format(MESSAGE_DETAG_PERSONS_SUCCESS, deletedPersonTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: detag the first tag of last person in the list
         * -> detag first tag
         * */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertTrue(expectedModel.equals(modelBeforeDeletingLast));
        assertTrue(lastPersonIndex.getZeroBased()==6);
        assertFalse(modelBeforeDeletingLast.getFilteredPersonList().size()==lastPersonIndex.getZeroBased());
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo detagging the last person in the list -> last person's tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: mixed case command word -> deleted */
        Model modelAfterDeletingLast = getModel();
        Tag removedPersonTag = removeTag(modelAfterDeletingLast, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_DETAG_PERSONS_SUCCESS, removedPersonTag);
        assertCommandSuccess("DeTaG "+ modelAfterDeletingLast.getFilteredPersonList().size() +
                        " t/" + removedPersonTag.tagName, modelAfterDeletingLast,
                expectedResultMessage);

        /* Case: undo detagging the last person in the list -> last person's tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo detagging the last person in the list -> last person detagged again */
        command = RedoCommand.COMMAND_WORD;
        removeTag(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: detag first tag the middle person in the list -> detagged */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing detag operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, detag index within bounds of address book and person list -> detagged */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        //assertCommandSuccess(command, expectedModel, MESSAGE_DETAG_PERSONS_SUCCESS);

        /* Case: filtered person list, detag index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DetagCommand.COMMAND_WORD + " " + invalidIndex + " " + PREFIX_TAG + deletedPersonTag.tagName;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid detag operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DetagCommand.COMMAND_WORD + " 0 " + PREFIX_TAG + deletedPersonTag;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DetagCommand.COMMAND_WORD + " -1 " + PREFIX_TAG + deletedPersonTag;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DetagCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " " + PREFIX_TAG +
                deletedPersonTag.tagName;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DetagCommand.COMMAND_WORD + " abc" + PREFIX_TAG +
                        deletedPersonTag, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DetagCommand.COMMAND_WORD + " 1 abc" + PREFIX_TAG +
                        deletedPersonTag, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

    }

    /**
     * Select the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the string of tag of person
     */
    private Tag removeTag(Model model, Index index) throws CommandException {
        ReadOnlyPerson person = getPerson(model, index);
        Tag targetTag = person.getTags().iterator().next();

        try {
            model.deleteTag(person, targetTag);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException edea) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_MISSING_TAG);
        }
        return targetTag;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) throws CommandException {
        Model expectedModel = getModel();
        assertTrue(expectedModel.getAddressBook().getPersonList().size()==7);
        Tag deletedPersonTag = removeTag(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DETAG_PERSONS_SUCCESS, deletedPersonTag);

        assertCommandSuccess(
                DetagCommand.COMMAND_WORD + " " + toDelete.getOneBased() + " " + PREFIX_TAG +
                        deletedPersonTag.tagName, expectedModel, expectedResultMessage);
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
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

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
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
