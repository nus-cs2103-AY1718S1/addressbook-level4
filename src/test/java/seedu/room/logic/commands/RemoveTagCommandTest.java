package seedu.room.logic.commands;

import static seedu.room.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_tagNameValid_success() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(VALID_TAG_FRIEND);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.removeTag(new Tag(VALID_TAG_FRIEND));

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameInvalid() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(INVALID_TAG);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_NOT_EXIST;

        assertCommandFailure(removeTagCommand, model, expectedMessage);
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code tagName}
     */
    private RemoveTagCommand prepareCommand(String tagName) {
        RemoveTagCommand command = new RemoveTagCommand(tagName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
