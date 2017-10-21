package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PinCommand}
 * and {@code UnpinCommand}.
 */
public class PinUnpinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.pinPerson(personToPin);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToPin);

        expectedModel.unpinPerson(personToUnpin);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListPin_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = preparePinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredListUnpin_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = prepareUnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyPinned_throwsCommandException() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.pinPerson(personToPin);
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_PERSON_ALREADY_PINNED);
    }

    @Test
    public void execute_personAlreadyUnpinned_throwsCommandException() throws Exception {
        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unpinPerson(personToUnpin);
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.pinPerson(personToPin);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        showFirstPersonOnly(model);

        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unpinPerson(personToPin);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PinCommand pinCommand = preparePinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_PERSON);
        assertTrue(pinFirstCommand.equals(pinFirstCommandCopy));
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand.equals(null));
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    /**
     * Returns a {@code PinCommand} with the parameter {@code index}.
     */
    private PinCommand preparePinCommand(Index index) {
        PinCommand pinCommand = new PinCommand(index);
        pinCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return pinCommand;
    }

    /**
     * Returns a {@code UnpinCommand} with the parameter {@code index}.
     */
    private UnpinCommand prepareUnpinCommand(Index index) {
        UnpinCommand unpinCommand = new UnpinCommand(index);
        unpinCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unpinCommand;
    }
}
