package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstLessonOnly;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

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
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listModuleCommand;
    private ListCommand listLocationCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listModuleCommand = new ListCommand("modules");
        listLocationCommand = new ListCommand("locations");

        listModuleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listLocationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listModuleCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MODULE_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllModules() {
        showFirstLessonOnly(model);
        assertCommandSuccess(listModuleCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MODULE_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllLocations() {
        assertCommandSuccess(listLocationCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.LOCATION_KEYWORD), expectedModel);
    }


}
