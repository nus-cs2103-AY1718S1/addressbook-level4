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

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsEmpty_success() {
        Model model = new ModelManager();
        model.getFilteredPersonList();
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_LIST_EMPTY, model);
    }

    @Test
    public void execute_sortingList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contacts by name in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
