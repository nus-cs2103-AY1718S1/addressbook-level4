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
 * Contains integration tests (interaction with the Model) and unit tests for ListReverseCommand.
 */
public class ListReverseCommandTest {
    private Model model;
    private Model expectedModel;
    private ListReverseCommand listUnderTestCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listUnderTestCommand = new ListReverseCommand();
        listUnderTestCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeFilterList() {
        expectedModel.listNameReversed();
        assertCommandSuccess(listUnderTestCommand, model, ListReverseCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
