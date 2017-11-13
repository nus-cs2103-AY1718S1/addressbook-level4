package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.executeAndRecover;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareRedoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Adoby7
/**
 * Test the redo function of DeleteCommand
 */
public class RedoDeleteCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_PERSON);

    @Before
    public void setUp() {
        executeAndRecover(model, deleteCommandOne, deleteCommandTwo);
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, deleteCommandOne, deleteCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
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
