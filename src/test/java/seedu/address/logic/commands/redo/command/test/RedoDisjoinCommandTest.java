package seedu.address.logic.commands.redo.command.test;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.logic.commands.CommandTestUtil.quitEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.DisjoinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

//@@author Adoby7
/**
 * Test the redo function of DisjoinCommand
 */
public class RedoDisjoinCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

    @Before
    public void setUp() throws Exception {
        joinEvents(model);
        disjoinCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        disjoinCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        Person firstPerson = (Person) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event firstEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandOne.assignValueForTest(firstPerson, firstEvent);

        Person secondPerson = (Person) model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event secondEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandTwo.assignValueForTest(secondPerson, secondEvent);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
            Collections.emptyList(), Arrays.asList(disjoinCommandTwo, disjoinCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // multiple commands in redoStack
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        quitEvent(expectedModel, person, event);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        person = (Person) expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        quitEvent(expectedModel, person, event);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
