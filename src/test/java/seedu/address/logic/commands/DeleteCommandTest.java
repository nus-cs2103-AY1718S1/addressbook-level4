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
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model model2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> personsToDelete1 = new ArrayList<>();
    private ArrayList<Index> perosnsToDelete2 = new ArrayList<>();

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model1.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personToDelete2 = model2.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        personsToDelete1.add(INDEX_FIRST_PERSON);
        perosnsToDelete2.add(INDEX_FIRST_PERSON);
        perosnsToDelete2.add(INDEX_SECOND_PERSON);

        DeleteCommand deleteCommand1 = prepareCommand(model1, personsToDelete1);
        DeleteCommand deleteCommand2 = prepareCommand(model2, perosnsToDelete2);


        String expectedMessage1 = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
        String expectedMessage2 = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model1.getAddressBook(), new UserPrefs());
        ModelManager expectedModel2 = new ModelManager(model2.getAddressBook(), new UserPrefs());

        expectedModel1.deletePerson(personToDelete);
        expectedModel2.deletePerson(personToDelete);
        expectedModel2.deletePerson(personToDelete2);

        assertCommandSuccess(deleteCommand1, model1, expectedMessage1, expectedModel1);
        assertCommandSuccess(deleteCommand2, model2, expectedMessage2, expectedModel2);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model1.getFilteredPersonList().size() + 1);
        personsToDelete1.clear();
        personsToDelete1.add(outOfBoundIndex);
        DeleteCommand deleteCommand = prepareCommand(model1, personsToDelete1);

        assertCommandFailure(deleteCommand, model1, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model1);

        ReadOnlyPerson personToDelete = model1.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(model1, personsToDelete1);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model1.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model1, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model1);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model1.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(model1, personsToDelete1);

        assertCommandFailure(deleteCommand, model1, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(first);
        DeleteCommand deleteSecondCommand = new DeleteCommand(second);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(first);
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
    private DeleteCommand prepareCommand(Model environment, ArrayList<Index> indexes) {

        DeleteCommand deleteCommand = new DeleteCommand(indexes);
        deleteCommand.setData(environment, new CommandHistory(), new UndoRedoStack());
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
