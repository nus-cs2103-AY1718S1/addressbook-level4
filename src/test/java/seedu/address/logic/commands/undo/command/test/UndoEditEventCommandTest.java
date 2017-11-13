package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyEvent;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
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
import seedu.address.logic.commands.UndoCommand;
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
 * Test the undo function of EditEventCommand
 */
public class UndoEditEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditEventDescriptor firstDescriptor = new EventDescriptorBuilder(SEVENTH).build();
    private final EditEventDescriptor secondDescriptor = new EventDescriptorBuilder(EIGHTH).build();
    private final EditEventCommand editEventCommandOne = new EditEventCommand(INDEX_FIRST_EVENT, firstDescriptor);
    private final EditEventCommand editEventCommandTwo = new EditEventCommand(INDEX_SECOND_EVENT, secondDescriptor);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, editEventCommandOne, editEventCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        editEventCommandOne.execute();
        editEventCommandTwo.execute();

        // multiple commands in undoStack
        modifyEvent(expectedModel, INDEX_FIRST_EVENT, SEVENTH);
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
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
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
