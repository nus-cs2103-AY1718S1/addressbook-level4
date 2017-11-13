package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyEvent;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.executeAndRecover;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareRedoCommand;
import static seedu.address.testutil.TypicalEvents.EIGHTH;
import static seedu.address.testutil.TypicalEvents.SEVENTH;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventDescriptorBuilder;

//@@author Adoby7
/**
 * Test the redo function of EditEventCommand
 */
public class RedoEditEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditEventDescriptor firstDescriptor = new EventDescriptorBuilder(SEVENTH).build();
    private final EditEventDescriptor secondDescriptor = new EventDescriptorBuilder(EIGHTH).build();
    private final EditEventCommand editEventCommandOne = new EditEventCommand(INDEX_FIRST_EVENT, firstDescriptor);
    private final EditEventCommand editEventCommandTwo = new EditEventCommand(INDEX_SECOND_EVENT, secondDescriptor);

    @Test
    public void execute() {
        executeAndRecover(model, editEventCommandOne, editEventCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, editEventCommandOne, editEventCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyEvent(expectedModel, INDEX_FIRST_EVENT, SEVENTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyEvent(expectedModel, INDEX_SECOND_EVENT, EIGHTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoEditEventCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoEditEventCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException {
            throw new DuplicateEventException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }
}
