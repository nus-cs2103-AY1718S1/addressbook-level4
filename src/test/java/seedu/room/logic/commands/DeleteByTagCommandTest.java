package seedu.room.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.tag.Tag;


public class DeleteByTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());


    @Test
    public void execute_validTag_success() throws IllegalValueException, CommandException {
        String tagToDelete = "friends";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, tag);
        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());

        expectedModel.deleteByTag(tag);

        assertCommandSuccess(deleteByTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws IllegalValueException, CommandException {
        String tagToDelete = "thisTagDoesNotExist";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        assertCommandFailure(deleteByTagCommand, model, Messages.MESSAGE_INVALID_TAG_FOUND);
    }

    @Test
    public void equals() throws IllegalValueException {
        DeleteByTagCommand deleteByTagFirstCommand = new DeleteByTagCommand("friends");
        DeleteByTagCommand deleteByTagSecondCommand = new DeleteByTagCommand("friends");

        // same object -> returns true
        assertTrue(deleteByTagFirstCommand.equals(deleteByTagSecondCommand));

        // different types -> returns false
        assertFalse(deleteByTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByTagFirstCommand.equals(null));

    }

    /**
     * Returns a {@code DeleteByTagCommand} with the parameter {@code index}.
     */
    private DeleteByTagCommand prepareCommand(String tagName) throws IllegalValueException {
        DeleteByTagCommand deleteByTagCommand = new DeleteByTagCommand(tagName);
        deleteByTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByTagCommand;
    }
}
