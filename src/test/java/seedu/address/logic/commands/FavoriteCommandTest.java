//@@author: giang
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

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FavoriteCommand}.
 */
public class FavoriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model, MESSAGE_EXECUTION_FAILURE
                + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavoriteCommand favoriteFirstCommand = new FavoriteCommand(INDEX_FIRST_PERSON);
        FavoriteCommand favoriteSecondCommand = new FavoriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommand));

        // same values -> returns true
        FavoriteCommand favoriteFirstCommandCopy = new FavoriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favoriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favoriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favoriteFirstCommand.equals(favoriteSecondCommand));
    }

    /**
     * Returns a {@code FavoriteCommand} with the parameter {@code index}.
     */
    private FavoriteCommand prepareCommand(Index index) {
        FavoriteCommand favoriteCommand = new FavoriteCommand(index);
        favoriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favoriteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
