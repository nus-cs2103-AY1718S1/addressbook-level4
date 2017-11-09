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

//@@author Jeremy
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAscendingNameCommand.
 */
public class ListAscendingNameCommandTest {
    private Model model;
    private Model expectedModel;
    private ListAscendingNameCommand listUnderTestCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listUnderTestCommand = new ListAscendingNameCommand();
        listUnderTestCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeFilterList() {
        expectedModel.listNameAscending();
        assertCommandSuccess(listUnderTestCommand, model, ListAscendingNameCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
