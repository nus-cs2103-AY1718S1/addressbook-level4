package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyPerson;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.PortraitCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PortraitPath;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

//@@author Adoby7
/**
 * Test the undo function of PortraitCommand
 */
public class UndoPortraitCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    private PortraitCommand portraitCommandOne;
    private PortraitCommand portraitCommandTwo;
    private Person modifiedFirstPerson;

    @Before
    public void setUp() throws Exception {
        PortraitPath firstPortrait = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
        PortraitPath secondPortrait = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        portraitCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, firstPortrait);
        portraitCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, secondPortrait);
        modifiedFirstPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_FIRST).build();
    }

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, portraitCommandOne, portraitCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        portraitCommandOne.execute();
        portraitCommandTwo.execute();

        // multiple commands in undoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, modifiedFirstPerson);
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
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, portraitCommandOne, portraitCommandTwo);
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
