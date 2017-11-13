package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyPerson;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.UndoCommand;
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
 * Test the undo function of EditCommand
 */
public class UndoEditCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder(IDA).build();
    private final EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder(HOON).build();
    private final EditCommand editCommandOne = new EditCommand(INDEX_FIRST_PERSON, firstDescriptor);
    private final EditCommand editCommandTwo = new EditCommand(INDEX_SECOND_PERSON, secondDescriptor);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, editCommandOne, editCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        editCommandOne.execute();
        editCommandTwo.execute();

        // multiple commands in undoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, IDA);
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
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, editCommandOne, editCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, editCommandOne, editCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
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
