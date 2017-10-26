package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.graph.GraphWrapper;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private GraphWrapper graphWrapper = new GraphWrapper();

    @Test
    public void execute_validTagNameWhichExists_success() throws Exception {
        String tagNameToBeRemoved = "owesMoney";
        RemoveTagCommand removeTagCommand = prepareCommand("owesMoney");

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagNameToBeRemoved);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagNameToBeRemoved);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagNameWhichDoesNotExists_throwsCommandException() throws Exception {
        String tagNameToBeRemoved = VALID_TAG_FRIEND + "ddd";
        RemoveTagCommand removeTagCommand = prepareCommand(tagNameToBeRemoved);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);
    }


    @Test
    public void equals() {
        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(VALID_TAG_FRIEND);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(VALID_TAG_HUSBAND);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveTagCommand removeFirstCommandCopy = new RemoveTagCommand(VALID_TAG_FRIEND);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code RemoveTagCommand} with the parameter {@code index}.
     */
    private RemoveTagCommand prepareCommand(String tagToBeRemoved) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToBeRemoved);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack(), graphWrapper);
        return removeTagCommand;
    }
}
