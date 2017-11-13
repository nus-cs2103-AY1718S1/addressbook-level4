package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.person.ListPinCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author Alim95
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPinCommand.
 */
public class ListPinCommandTest {

    private Model model;
    private Model expectedModel;
    private ListPinCommand listPinCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listPinCommand = new ListPinCommand();
        listPinCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_PINNED);
    }

    @Test
    public void execute_listIsNotFiltered_showsPinnedList() {
        assertCommandSuccess(listPinCommand, model, ListPinCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsPinnedList() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listPinCommand, model, ListPinCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
