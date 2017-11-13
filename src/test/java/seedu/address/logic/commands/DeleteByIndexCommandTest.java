package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.StorageUtil.getDummyStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByIndexCommand}.
 */
public class DeleteByIndexCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String people = personToDelete.toString();
        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(deleteByIndexCommand.MESSAGE_DELETE_PERSON_SUCCESS, "\n" + people);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
    }

    //@@author sarahnzx
    @Test
    public void execute_multipleValidIndicesUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = null;
        String people = "";
        List<Index> indexList = new ArrayList<>();


        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (int i = 0; i < 3; i++) {
            Index indexToDelete = Index.fromZeroBased(i);
            personToDelete = model.getFilteredPersonList().get(indexToDelete.getZeroBased());
            expectedModel.deletePerson(personToDelete);
            people += "\n" + personToDelete.toString();
            indexList.add(indexToDelete);
        }

        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(indexList);
        String expectedMessage = String.format(deleteByIndexCommand.MESSAGE_DELETE_PERSON_SUCCESS, people);
        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
    }
    //@@author

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author sarahnzx
    @Test
    public void execute_multipleIndicesWithInvalidIndexUnfilteredList_throwsCommandException() throws Exception {
        List<Index> indexList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Index index = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
            indexList.add(index);
        }
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        indexList.add(outOfBoundIndex);
        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(indexList);
        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    //@@author

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String people = personToDelete.toString();
        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(deleteByIndexCommand.MESSAGE_DELETE_PERSON_SUCCESS, "\n" + people);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteByIndexCommand deleteByIndexCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteByIndexCommand deleteFirstCommand = new DeleteByIndexCommand(Arrays.asList(INDEX_FIRST_PERSON));
        DeleteByIndexCommand deleteSecondCommand = new DeleteByIndexCommand(Arrays.asList(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByIndexCommand deleteFirstCommandCopy = new DeleteByIndexCommand(Arrays.asList(INDEX_FIRST_PERSON));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteByIndexCommand} with the parameter {@code index}.
     */
    private DeleteByIndexCommand prepareCommand(Index index) {
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(Arrays.asList(index));
        deleteByIndexCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return deleteByIndexCommand;
    }

    //@@author sarahnzx
    private DeleteByIndexCommand prepareCommand(Collection<Index> indexes) {
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(indexes);
        deleteByIndexCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return deleteByIndexCommand;
    }
    //@@author

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
