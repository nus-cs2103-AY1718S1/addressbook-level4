package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.person.PinCommand.MESSAGE_PIN_PERSON_SUCCESS;
import static seedu.address.logic.commands.person.UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getUnpinPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.PinCommand;
import seedu.address.logic.commands.person.UnpinCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
//@@author Alim95
public class PinUnpinCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_PIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_UNPIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE);

    @Test
    public void pinUnpin() {
        /* ----------------- Performing pin operation while an unfiltered list is being shown -------------------- */

        /* Case: pin the first person in the list, command with leading spaces and trailing spaces -> pinned */
        Model expectedModel = getModel();
        String command = "     " + PinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertPinCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: pin the last person in the list -> pinned */
        Model modelBeforePinningLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforePinningLast);
        assertPinCommandSuccess(lastPersonIndex);

        /* ----------------- Performing unpin operation while an unfiltered list is being shown -------------------- */

        /* Case: unpin the first person in the list,
         command with leading spaces and trailing spaces -> unpinned */
        expectedModel = getModel();
        command = "     " + UnpinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);
        assertUnpinCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: unpin the last person in the list -> unpinned */
        Model modelBeforeUnpinningLast = getModel();
        lastPersonIndex = getLastIndex(modelBeforeUnpinningLast);
        assertUnpinCommandSuccess(lastPersonIndex);

        /* ------------------ Performing pin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertPinCommandSuccess(index);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = PinCommand.COMMAND_WORD + " " + invalidIndex;
        assertPinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, pin person already pinned
         * -> rejected
         */
        command = PinCommand.COMMAND_WORD + " 1";
        assertPinCommandFailure(command, Messages.MESSAGE_PERSON_ALREADY_PINNED);

        /* ------------------ Performing unpin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertUnpinCommandSuccess(index);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        command = UnpinCommand.COMMAND_WORD + " " + invalidIndex;
        assertUnpinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, unpin person not pinned
         * -> rejected
         */
        command = UnpinCommand.COMMAND_WORD + " 1";
        assertPinCommandFailure(command, Messages.MESSAGE_PERSON_ALREADY_UNPINNED);

        /* ---------------------------- Performing invalid pin and unpin operation ------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = PinCommand.COMMAND_WORD + " 0";
        assertPinCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        command = UnpinCommand.COMMAND_WORD + " 0";
        assertUnpinCommandFailure(command, MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PinCommand.COMMAND_WORD + " -1";
        assertPinCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        command = UnpinCommand.COMMAND_WORD + " -1";
        assertUnpinCommandFailure(command, MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = PinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertPinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command = UnpinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertUnpinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertPinCommandFailure(PinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        assertUnpinCommandFailure(UnpinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertPinCommandFailure(PinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        assertUnpinCommandFailure(UnpinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertPinCommandFailure("PiN 1", MESSAGE_UNKNOWN_COMMAND);
        assertPinCommandFailure("uNPiN 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Pins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the pinned person
     */
    private ReadOnlyPerson pinPerson(Model model, Index index) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson targetPerson = lastShownList.get(index.getZeroBased());
        try {
            model.pinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Pins the person at {@code toPin} by creating a default {@code PinCommand} using {@code toPin} and
     * performs the same verification as {@code assertPinCommandSuccess(String, Model, String)}.
     *
     * @see PinUnpinCommandSystemTest#assertPinCommandSuccess(String, Model, String)
     */
    private void assertPinCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);

        assertPinCommandSuccess(
                PinCommand.COMMAND_WORD + " " + toPin.getOneBased(), expectedModel, expectedResultMessage);
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertPinCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertPinCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertPinCommandSuccess(String, Model, String)}
     * except that the browser url and selected card are expected to update accordingly depending
     * on the card at {@code expectedSelectedCardIndex}.
     *
     * @see PinUnpinCommandSystemTest#assertPinCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertPinCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertPinCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Unpins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the unpinned person
     */
    private ReadOnlyPerson unpinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getUnpinPerson(model, index);
        try {
            model.unpinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Unpins the person at {@code toUnpin} by creating a default {@code UnpinCommand} using {@code toUnpin} and
     * performs the same verification as {@code assertUnpinCommandSuccess(String, Model, String)}.
     *
     * @see PinUnpinCommandSystemTest#assertUnpinCommandSuccess(String, Model, String)
     */
    private void assertUnpinCommandSuccess(Index toUnpin) {
        Model expectedModel = getModel();
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, toUnpin);
        String expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);

        assertUnpinCommandSuccess(
                UnpinCommand.COMMAND_WORD + " " + toUnpin.getOneBased(), expectedModel, expectedResultMessage);
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertUnpinCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertUnpinCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertUnpinCommandSuccess(String, Model, String)}
     * except that the browser url and selected card are expected to update accordingly depending
     * on the card at {@code expectedSelectedCardIndex}.
     *
     * @see PinUnpinCommandSystemTest#assertUnpinCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertUnpinCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertUnpinCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
