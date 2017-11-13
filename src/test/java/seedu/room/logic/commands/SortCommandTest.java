package seedu.room.logic.commands;

import static junit.framework.TestCase.fail;
import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Before;
import org.junit.Test;

import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;


//@@author sushinoya
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommandByName;
    private SortCommand sortCommandByPhone;
    private SortCommand sortCommandByInvalidField;
    private String sortCriteriaDefault;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        sortCriteriaDefault = "name";

        sortCommandByName = new SortCommand(sortCriteriaDefault);
        sortCommandByName.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandByPhone = new SortCommand("phone");
        sortCommandByPhone.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandByInvalidField = new SortCommand("tag");
        sortCommandByInvalidField.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortByName_onAlreadySortedByName() {
        String failureMessage = String.format(SortCommand.MESSAGE_FAILURE, sortCriteriaDefault);
        assertCommandFailure(sortCommandByName, model, failureMessage);
    }

    @Test
    public void execute_sortByPhone_onAlreadySortedByName() {
        String successMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone");
        try {
            expectedModel.sortBy("phone");
        } catch (AlreadySortedException e) {
            fail("This should never be called");
        }
        assertCommandSuccess(sortCommandByPhone, model, successMessage, expectedModel);
    }

    @Test
    public void execute_sortByInvalidField() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertCommandFailure(sortCommandByInvalidField, model, failureMessage);
    }

}
