package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.logic.commands.CommandTestUtil.quitEvent;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DisjoinCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.HaveParticipateEventException;

// @@author Adoby7
/**
 * Test the undo function of DisjoinCommand
 */
public class UndoDisjoinCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
    private Model modelCopy;

    @Before
    public void setUp() {
        joinEvents(model);
        modelCopy = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
    }

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, disjoinCommandOne, disjoinCommandTwo);
        disjoinCommandOne.execute();
        disjoinCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        quitEvent(expectedModel, INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void joinEvent(Person person, Event event) throws PersonHaveParticipateException {
            throw new PersonHaveParticipateException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void joinEvent(Person person, Event event) throws HaveParticipateEventException {
            throw new HaveParticipateEventException();
        }
    }
}
