package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.logic.commands.CommandTestUtil.quitEvent;
import static seedu.address.logic.commands.UndoRedoCommandUtil.assertCommandAssertionError;
import static seedu.address.logic.commands.UndoRedoCommandUtil.prepareRedoCommand;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DisjoinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.NotParticipateEventException;

//@@author Adoby7
/**
 * Test the redo function of DisjoinCommand
 */
public class RedoDisjoinCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

    @Before
    public void setUp() throws Exception {
        joinEvents(model);
        Person firstPerson = (Person) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event firstEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandOne.assignValueForTest(firstPerson, firstEvent);

        Person secondPerson = (Person) model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event secondEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandTwo.assignValueForTest(secondPerson, secondEvent);
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, disjoinCommandOne, disjoinCommandTwo);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // multiple commands in redoStack
        quitEvent(expectedModel, INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        quitEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void quitEvent(Person person, Event event) throws PersonNotParticipateException {
            throw new PersonNotParticipateException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void quitEvent(Person person, Event event) throws NotParticipateEventException {
            throw new NotParticipateEventException();
        }
    }
}
