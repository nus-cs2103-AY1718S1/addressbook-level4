package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.PinCommand.MESSAGE_PIN_PERSON_SUCCESS;
import static seedu.address.logic.commands.UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_PINNED_PERSONS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


public class PinCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_PIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE);

    @Test
    public void pinAndUnpin() {
        /* ----------------- Performing pin operation while an unfiltered list is being shown -------------------- */

        /* Case: pin the first person in the list, command with leading spaces and trailing spaces -> pinned */
        Model expectedModel = getModel();
        String command = "     " + PinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /*Case: unpin the first person in the list, command with leading spaces and trailing spaces -> unpinned */
        command = "     " + UnpinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        pinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: pin the last person in the list -> pinned */
        Model modelBeforePinningLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforePinningLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo pinning the last person in the list -> last person unpinned */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: redo pinning the last person in the list -> last person pinned again */
        command = RedoCommand.COMMAND_WORD;
        pinPerson(modelBeforePinningLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(modelBeforePinningLast, INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: pin the middle person in the list -> pinned */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(getModel(), INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* ------------------ Performing pin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(getModel(), INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = PinCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------- Performing multiple pin and unpin operations------------------------------- */

        /* Case: Pins last 3 persons and unpins them accordingly */
        //Pin last person
        showAllPersons();
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Pin second last person
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Pin third last person
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin third person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_THIRD_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_THIRD_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin second person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_SECOND_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin first person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid pin operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = PinCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PinCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = PinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(PinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(PinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("PiN 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Pins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the pinned person
     */
    private ReadOnlyPerson pinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = model.getFilteredPersonList().get(index.getZeroBased());
        try {
            model.pinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (CommandException ce) {
            throw new AssertionError("targetPerson unable to be pinned");
        }
        return targetPerson;
    }

    /**
     * Unpins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the unpinned person
     */
    private ReadOnlyPerson unpinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = model.getFilteredPersonList().get(index.getZeroBased());
        try {
            model.unpinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (CommandException ce) {
            throw new AssertionError("targetPerson unable to be unpinned");
        }
        return targetPerson;
    }

    /**
     * Removes all pins in the model
     */
    private void removeAllPin() {
        Model unpinModel = getModel();
        List<ReadOnlyPerson> list = unpinModel.getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS);
        for (ReadOnlyPerson person: list) {
            removePin(unpinModel, person);
        }
    }

    /**
     * Removes pin tag from a person
     * @param model
     * @param person
     */
    private void removePin(Model model, ReadOnlyPerson person) {
        Set<Tag> listTags = person.getTags();
        try {
            for (Tag tag : listTags) {
                if ("Pinned".equals(tag.tagName)) {
                    model.unpinPerson(person);
                }

            }
        } catch (CommandException ce) {
            throw new AssertionError("CommandException error");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("PersonNotFoundException error");
        }
    }

    /**
     * Unpins the person at {@code toUnpin} by creating a default {@code UnpinCommand} using {@code toUnpin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see PinCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertUnpinCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);

        assertCommandSuccess(
                PinCommand.COMMAND_WORD + " " + toPin.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Pins the person at {@code toPin} by creating a default {@code PinCommand} using {@code toPin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see PinCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);

        assertCommandSuccess(
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
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
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
     *
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
