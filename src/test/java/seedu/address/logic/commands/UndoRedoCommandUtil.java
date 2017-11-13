package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.UndoRedoStackUtil.prepareStack;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

// @@author Adoby7

/**
 * Refactor the undo/redo tests. Extract some common parts as util
 */
public class UndoRedoCommandUtil {
    public static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    public static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    /**
     * Create a RedoCommand by given model and 2 commands
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        commandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        UndoRedoStack undoRedoStack = prepareStack(Collections.emptyList(), Arrays.asList(commandTwo, commandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        return redoCommand;
    }

    /**
     * Create an UndoCommand by given model and 2 commands
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        commandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        UndoRedoStack undoRedoStack = prepareStack(Arrays.asList(commandOne, commandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        return undoCommand;
    }


    /**
     * Execute the undo/redo part with only assertion error
     */
    public static void assertCommandAssertionError(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (AssertionError ae) {
            assertEquals(expectedMessage, ae.getMessage());
        } catch (Exception e) {
            fail("Impossible");
        }
    }

    /**
     * Execute commands to store some data, but does not affect model
     */
    public static void executeAndRecover(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        Model modelCopy = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        commandOne.setData(modelCopy, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(modelCopy, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        try {
            commandOne.execute();
            commandTwo.execute();
        } catch (CommandException e) {
            fail("Impossible");
        }

    }
}
