//@@author Houjisan
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class TagsCommandTest {

    private Model model;
    private Model expectedModel;
    private TagsCommand tagsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        tagsCommand = new TagsCommand();
        tagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_showTags_success() {
        assertCommandSuccess(tagsCommand, model, TagsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
