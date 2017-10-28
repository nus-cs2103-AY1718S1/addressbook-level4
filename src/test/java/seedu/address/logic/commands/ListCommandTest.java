package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listModuleCommand;
    private ListCommand listLocationCommand;
    private ListCommand listMarkedCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listModuleCommand = new ListCommand("module");
        listLocationCommand = new ListCommand("location");
        listMarkedCommand = new ListCommand("marked");

        listModuleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listLocationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listMarkedCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_list_marked() throws DuplicateLessonException {

        expectedModel.updateFilteredLessonList(new MarkedListPredicate());
        assertCommandSuccess(listMarkedCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MARKED_LIST_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllModules() {
        expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));
        assertCommandSuccess(listModuleCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.MODULE_KEYWORD), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllLocations() {
        expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
        assertCommandSuccess(listLocationCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.LOCATION_KEYWORD), expectedModel);
    }


}
