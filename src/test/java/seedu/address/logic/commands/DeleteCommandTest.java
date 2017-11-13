//@@author hthjthtrh
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Test
    public void execute_validIndexUnfilteredListSinglePerson_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListSingleOutOfBound_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void equals() {

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(testIndexes);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(testIndexes);

        ArrayList<Index> testIndexes2 = new ArrayList<>();
        testIndexes2.add(TypicalIndexes.INDEX_SECOND_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(testIndexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

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
    private DeleteCommand prepareCommand(ArrayList<Index> index) {
        //testIndexes.clear();
        DeleteCommand deleteCommand = new DeleteCommand(testIndexes);
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
//@@author
