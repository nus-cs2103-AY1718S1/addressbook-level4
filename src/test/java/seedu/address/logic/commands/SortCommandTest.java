package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author limcel
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        sortCommand = prepareCommand(model);
    }

    @Test
    public void execute_sortingList_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        sortCommand = prepareCommand(model);
        CommandResult result = sortCommand.execute();
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }

    //======================================== HELPER METHODS ==========================================
    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contacts by name in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
