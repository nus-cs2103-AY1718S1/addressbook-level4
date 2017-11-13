package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalRecycleBin;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code BinDeleteCommand}.
 */
public class BinDeleteCommandTest {

    private Model model;

    //@@author frozventus
    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author frozventus-reused
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author frozventus
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author frozventus-reused
    @Test
    public void equals() {
        BinDeleteCommand binDeleteFirstCommand = new BinDeleteCommand(INDEX_FIRST_PERSON);
        BinDeleteCommand binDeleteSecondCommand = new BinDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(binDeleteFirstCommand.equals(binDeleteFirstCommand));

        // same values -> returns true
        BinDeleteCommand deleteFirstCommandCopy = new BinDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(binDeleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(binDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(binDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(binDeleteFirstCommand.equals(binDeleteSecondCommand));
    }

    /**
     * Returns a {@code BinDeleteCommand} with the parameter {@code index}.
     */
    private BinDeleteCommand prepareCommand(Index index) {
        BinDeleteCommand binDeleteCommand = new BinDeleteCommand(index);
        binDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return binDeleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
