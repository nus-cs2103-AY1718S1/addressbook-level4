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

public class UnfavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidPerson_success() throws Exception {
        favouriteFirstPerson(model);
        ReadOnlyPerson personToUnfavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_SUCCESS,
                personToUnfavourite);
        CommandResult commandResult = unfavouriteCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_validIndexInvalidPerson_success() throws Exception {
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            unfavouriteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_FAIL, e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);

        try {
            unfavouriteCommand.execute();
            fail("The expected CommandException was not thrown");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        UnfavouriteCommand favouriteFirstCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        UnfavouriteCommand favouriteSecondCommand = new UnfavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand deleteFirstCommandCopy = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code UnfavouriteCommand} with the parameter {@code index}.
     */
    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
// @@author
