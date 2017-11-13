package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyPerson;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.executeAndRecover;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareRedoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

//@@author Adoby7
/**
 * Test the redo function of EditCommand
 */
public class RedoEditCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditCommand.EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder(IDA).build();
    private final EditCommand.EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder(HOON).build();
    private final EditCommand editCommandOne = new EditCommand(INDEX_FIRST_PERSON, firstDescriptor);
    private final EditCommand editCommandTwo = new EditCommand(INDEX_SECOND_PERSON, secondDescriptor);

    @Test
    public void execute() {
        executeAndRecover(model, editCommandOne, editCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, editCommandOne, editCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, IDA);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyPerson(expectedModel, INDEX_SECOND_PERSON, HOON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoEditCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoEditCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, editCommandOne, editCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, editCommandOne, editCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }
}
