package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstOverdueDebtPersonOnly;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author lawwman
public class OverdueListCommandTest extends CommandTest {

    private Model expectedModel;
    private OverdueListCommand overdueListCommand;

    @Override
    @Before
    public void setUp() {
        ListObserver.init(model);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        overdueListCommand = new OverdueListCommand();
        overdueListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model.setCurrentListName("overduelist");
        assertCommandSuccess(overdueListCommand, model,
                ListObserver.OVERDUELIST_NAME_DISPLAY_FORMAT + OverdueListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstOverdueDebtPersonOnly(model);
        assertCommandSuccess(overdueListCommand, model,
                ListObserver.OVERDUELIST_NAME_DISPLAY_FORMAT + OverdueListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
