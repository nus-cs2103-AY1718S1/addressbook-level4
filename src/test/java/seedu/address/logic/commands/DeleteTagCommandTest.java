package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagUnfilteredList_success() throws Exception {
        Tag tagForDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(tagForDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_ALL_TAG_SUCCESS, tagForDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagForDelete);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tag}.
     */
    private DeleteTagCommand prepareCommand(Tag tag) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tag);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }

}

