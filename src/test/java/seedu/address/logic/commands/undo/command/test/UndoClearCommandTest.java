package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author Adoby7
/**
 * Test the undo function of ClearCommand
 */
public class UndoClearCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final ClearCommand clearCommandOne = new ClearCommand();
    private final ClearCommand clearCommandTwo = new ClearCommand();

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, clearCommandOne, clearCommandTwo);
        Model expectedModel = new ModelManager(new AddressBook(), getTypicalEventList(), new UserPrefs());
        clearCommandOne.execute();
        clearCommandTwo.execute();

        // multiple commands in undoStack
        expectedModel.resetData(new AddressBook(), new EventList());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
