package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.addEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.FIFTH;
import static seedu.address.testutil.TypicalEvents.SIXTH;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;

//@@author Adoby7
/**
 * Test the undo function of AddEventCommand
 */
public class UndoAddEventCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddEventCommand addCommandOne = new AddEventCommand(FIFTH);
    private final AddEventCommand addCommandTwo = new AddEventCommand(SIXTH);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, addCommandOne, addCommandTwo);
        addCommandOne.execute();
        addCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        addEvent(expectedModel, FIFTH);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
