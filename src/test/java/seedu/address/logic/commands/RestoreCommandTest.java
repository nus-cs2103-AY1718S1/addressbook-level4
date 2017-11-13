package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalRecycleBin;

import org.junit.Before;
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code RestoreCommand}.
 */
public class RestoreCommandTest {

    private Model model;

    //@@author frozventus
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    //@@author frozventus-reused
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author frozventus
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);
        showNoPerson(expectedModel);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author frozventus-reused
    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        RestoreCommand restoreFirstCommandCopy = new RestoreCommand(INDEX_FIRST_PERSON);
        assertTrue(restoreFirstCommand.equals(restoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(restoreFirstCommand.equals(restoreSecondCommand));
    }

    /**
     * Returns a {@code RestoreCommand} with the parameter {@code index}.
     */
    private RestoreCommand prepareCommand(Index index) {
        RestoreCommand restoreCommand = new RestoreCommand(index);
        restoreCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return restoreCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
