package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.StorageUtil.getNullStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnFavoriteCommand}.
 */
public class UnFavoriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUnFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnFavoriteCommand unFavoriteCommand = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(
                UnFavoriteCommand.MESSAGE_UNFAVORITE_PERSON_SUCCESS, "\n" + personToUnFavorite.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.toggleFavoritePerson(personToUnFavorite, UnFavoriteCommand.COMMAND_WORD);

        assertCommandSuccess(unFavoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnFavoriteCommand unFavoriteCommand = prepareCommand(Arrays.asList(outOfBoundIndex));

        assertCommandFailure(unFavoriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ReadOnlyPerson personToUnFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnFavoriteCommand unFavoriteCommand = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(
                UnFavoriteCommand.MESSAGE_UNFAVORITE_PERSON_SUCCESS, "\n" + personToUnFavorite.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.toggleFavoritePerson(personToUnFavorite, UnFavoriteCommand.COMMAND_WORD);

        assertCommandSuccess(unFavoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnFavoriteCommand unFavoriteCommand = prepareCommand(Arrays.asList(outOfBoundIndex));

        assertCommandFailure(unFavoriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnFavoriteCommand unFavoriteFirstCommand = new UnFavoriteCommand(Arrays.asList(INDEX_FIRST_PERSON));
        UnFavoriteCommand favoriteSecondCommand = new UnFavoriteCommand(Arrays.asList(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(unFavoriteFirstCommand.equals(unFavoriteFirstCommand));

        // same values -> returns true
        UnFavoriteCommand unFavoriteFirstCommandCopy = new UnFavoriteCommand(Arrays.asList(INDEX_FIRST_PERSON));
        assertTrue(unFavoriteFirstCommand.equals(unFavoriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unFavoriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unFavoriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unFavoriteFirstCommand.equals(favoriteSecondCommand));
    }

    /**
     * Returns a {@code UnFavoriteCommand} with the parameter {@code index}.
     */
    private UnFavoriteCommand prepareCommand(List<Index> indexList) {
        UnFavoriteCommand unFavoriteCommand = new UnFavoriteCommand(indexList);
        unFavoriteCommand.setData(model, getNullStorage(), new CommandHistory(), new UndoRedoStack());
        return unFavoriteCommand;
    }
}
