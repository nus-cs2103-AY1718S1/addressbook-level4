package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.addPerson;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Adoby7
/**
 * Test the undo function of AddCommand
 */
public class UndoAddCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddCommand addCommandOne = new AddCommand(HOON);
    private final AddCommand addCommandTwo = new AddCommand(IDA);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, addCommandOne, addCommandTwo);
        addCommandOne.execute();
        addCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        addPerson(expectedModel, HOON);
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
        public void deletePerson(ReadOnlyPerson person) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deletePerson(ReadOnlyPerson person) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
