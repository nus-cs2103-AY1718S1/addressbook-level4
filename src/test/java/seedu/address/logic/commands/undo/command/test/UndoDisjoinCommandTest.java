package seedu.address.logic.commands.undo.command.test;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.addPerson;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.logic.commands.CommandTestUtil.quitEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.DisjoinCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

// @@author Adoby7
/**
 * Test the undo function of DisjoinCommand
 */
public class UndoDisjoinCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
    private Model modelCopy;

    @Before
    public void setUp() {
        disjoinCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        disjoinCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        joinEvents(model);
        modelCopy = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
            Arrays.asList(disjoinCommandOne, disjoinCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        disjoinCommandOne.execute();
        disjoinCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        quitEvent(expectedModel, person, event);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
