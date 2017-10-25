package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_tagNameValid_success() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(VALID_TAG_FRIEND);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.removeTag(new Tag(VALID_TAG_FRIEND));

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
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
