package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveCommand}.
 */
public class RemoveCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeTag_success() throws Exception {
        Tag tagToRemove = new Tag("friends");

        RemoveCommand removeCommand = prepareCommand(tagToRemove);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_SUCCESS, tagToRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagToRemove);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidTag() throws Exception {
        Tag tagToRemove = new Tag("enemy");

        RemoveCommand removeCommand = prepareCommand(tagToRemove);

        String expectedMessage =  "Tag: " + tagToRemove.toString() + RemoveCommand.MESSAGE_TAG_NOT_FOUND;

        assertCommandFailure(removeCommand, model, expectedMessage);
    }

    @Test
    public void equals() throws Exception{
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("owesMoney");
        RemoveCommand removeFirstCommand = new RemoveCommand(firstTag);
        RemoveCommand removeSecondCommand = new RemoveCommand(secondTag);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }


    /**
     * Returns a {@code RemoveCommand} with the parameter {@code tag}.
     */
    private RemoveCommand prepareCommand(Tag tag) {
        RemoveCommand removeCommand = new RemoveCommand(tag);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}