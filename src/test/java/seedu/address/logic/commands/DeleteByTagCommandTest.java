package seedu.address.logic.commands;


import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class DeleteByTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validTag_success() throws IllegalValueException, CommandException {
        String tagToDelete = "friends";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, tag);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

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
