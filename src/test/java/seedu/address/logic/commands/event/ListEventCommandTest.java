package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author junyango
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListEventCommandTest {

    private Model model;
    private Model expectedModel;
    private ListEventCommand listEventCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listEventCommand = new ListEventCommand();
        listEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_EVENT_SUCCESS, expectedModel);
    }
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstEventOnly(model);
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_EVENT_SUCCESS, expectedModel);
    }
}
