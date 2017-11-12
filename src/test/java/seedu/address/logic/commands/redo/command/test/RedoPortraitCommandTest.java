package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.modifyPerson;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.executeAndRecover;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareRedoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.PortraitCommand;
import seedu.address.logic.commands.RedoCommand;
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
 * Test the redo function of PortraitCommand
 */
public class RedoPortraitCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private PortraitCommand portraitCommandOne;
    private PortraitCommand portraitCommandTwo;
    private Person modifiedFirstPerson;
    private Person modifiedSecondPerson;

    @Before
    public void setUp() throws Exception {
        PortraitPath firstPortrait = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
        PortraitPath secondPortrait = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        portraitCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, firstPortrait);
        portraitCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, secondPortrait);
        modifiedFirstPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_FIRST).build();
        modifiedSecondPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_SECOND_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_SECOND).build();
    }

    @Test
    public void execute() {
        executeAndRecover(model, portraitCommandOne, portraitCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, portraitCommandOne, portraitCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, modifiedFirstPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyPerson(expectedModel, INDEX_SECOND_PERSON, modifiedSecondPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoPortraitCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoPortraitCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, portraitCommandOne, portraitCommandTwo);
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
