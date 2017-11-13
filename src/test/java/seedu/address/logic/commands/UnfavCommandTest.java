//@@author siri99
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
//import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnfavCommand}.
 */
public class UnfavCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /*
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUnfav = model.getFilteredFavouritePersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavCommand unfavCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToUnfav);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeFavouritePerson(personToUnfav);

        assertCommandSuccess(unfavCommand, model, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavCommand unfavCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ReadOnlyPerson personToFav = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavCommand favCommand = prepareFavCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFavouritePerson(personToFav);
        showFirstFavouritePersonOnly(model);

        ReadOnlyPerson personToUnfav = model.getFilteredFavouritePersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavCommand unfavCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToUnfav);

        //Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeFavouritePerson(personToUnfav);
        showNoPerson(expectedModel);

        assertCommandSuccess(unfavCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnfavCommand unfavCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnfavCommand unfavFirstCommand = new UnfavCommand(INDEX_FIRST_PERSON);
        UnfavCommand unfavSecondCommand = new UnfavCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unfavFirstCommand.equals(unfavFirstCommand));

        // same values -> returns true
        UnfavCommand unfavFirstCommandCopy = new UnfavCommand(INDEX_FIRST_PERSON);
        assertTrue(unfavFirstCommand.equals(unfavFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unfavFirstCommand.equals(unfavSecondCommand));
    }

    /**
     * Returns a {@code UnfavCommand} with the parameter {@code index}.
     */
    private UnfavCommand prepareCommand(Index index) {
        UnfavCommand unfavCommand = new UnfavCommand(index);
        unfavCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredFavouritePersonList(p -> false);

        assert model.getFilteredFavouritePersonList().isEmpty();
    }

    /**
     * Returns a {@code FavCommand} with the parameter {@code index}.
     */
    private FavCommand prepareFavCommand(Index index) {
        FavCommand favCommand = new FavCommand(index);
        favCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favCommand;
    }
}
//@@author siri99

