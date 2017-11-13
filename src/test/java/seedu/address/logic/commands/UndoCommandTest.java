package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.GuiUnitTest;

public class UndoCommandTest extends GuiUnitTest {
    private static CommandHistory emptyCommandHistory;
    private static UndoRedoStack emptyStack;

    private Model model;
    private DeleteCommand deleteCommandOne;
    private DeleteCommand deleteCommandTwo;

    @Before
    public void setUp() {
        emptyCommandHistory = new CommandHistory();
        emptyStack = new UndoRedoStack();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommandTwo = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommandOne.setData(model, emptyCommandHistory, emptyStack, null);
        deleteCommandTwo.setData(model, emptyCommandHistory, emptyStack, null);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, emptyCommandHistory, undoRedoStack, null);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
