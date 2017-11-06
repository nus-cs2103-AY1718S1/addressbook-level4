package seedu.address.logic.commands;

// @@author itsdickson

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidPerson_success() throws Exception {
        ReadOnlyPerson personToFavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS, personToFavourite);
        CommandResult commandResult = favouriteCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_validIndexInvalidPerson_success() throws Exception {
        favouriteFirstPerson(model);
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            favouriteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_FAIL, e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        try {
            favouriteCommand.execute();
            fail("The expected CommandException was not thrown");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand deleteFirstCommandCopy = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code FavouriteCommand} with the parameter {@code index}.
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
// @@author
