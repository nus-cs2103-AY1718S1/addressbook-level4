package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private List<ReadOnlyPerson> personsToDelete = new ArrayList<>();
    private HashSet<Integer> zeroBasedIndexes = new HashSet<>();

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        personsToDelete.add(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(zeroBasedIndexes);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personsToDelete.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, personsToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePersons(personsToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        personsToDelete.clear();
        zeroBasedIndexes.remove(INDEX_FIRST_PERSON.getZeroBased());

    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        zeroBasedIndexes.add(outOfBoundIndex.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(zeroBasedIndexes);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        zeroBasedIndexes.remove(outOfBoundIndex.getZeroBased());
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        personsToDelete.add(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(zeroBasedIndexes);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personsToDelete.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, personsToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePersons(personsToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        personsToDelete.clear();
        zeroBasedIndexes.remove(INDEX_FIRST_PERSON.getZeroBased());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        zeroBasedIndexes.add(outOfBoundIndex.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(zeroBasedIndexes);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        zeroBasedIndexes.remove(outOfBoundIndex.getZeroBased());
    }

    @Test
    public void equals() {
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteFirstCommand = new DeleteCommand(zeroBasedIndexes);

        // New HashSet created to prevent multiple HashSets referencing to the same HashSet
        HashSet<Integer> zeroBasedIndexes2 = new HashSet<>();
        zeroBasedIndexes2.add(INDEX_SECOND_PERSON.getZeroBased());
        DeleteCommand deleteSecondCommand = new DeleteCommand(zeroBasedIndexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteSecondCommand.equals(deleteSecondCommand));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(HashSet<Integer> zeroBasedIndexes) {
        DeleteCommand deleteCommand = new DeleteCommand(new HashSet<>(zeroBasedIndexes));
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
