package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private DeleteCommand deleteCommandOne;
    private DeleteCommand deleteCommandTwo;
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Before
    public void setUp() {

        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        deleteCommandOne = new DeleteCommand(testIndexes);

        ArrayList<Index> testIndexes1 = new ArrayList<>();
        testIndexes1.add(TypicalIndexes.INDEX_SECOND_PERSON);
        deleteCommandTwo = new DeleteCommand(testIndexes1);

        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    //@@author hthjthtrh
    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //need to refresh the Undo command since previously its step was reduced to 0
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack. Refresh the undo command again
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandFailure(undoCommand, model, MESSAGE_EXECUTION_FAILURE + UndoCommand.MESSAGE_FAILURE);
    }
    //@@author

}
