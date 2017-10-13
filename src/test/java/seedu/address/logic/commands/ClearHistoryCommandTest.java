package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ModelManager;

public class ClearHistoryCommandTest {
    private ClearHistoryCommand clearHistoryCommand;
    private CommandHistory history;
    private UndoRedoStack undoRedoStack;

    @Before
    public void setUp() {
        history = new CommandHistory();
        undoRedoStack = new UndoRedoStack();
        clearHistoryCommand = new ClearHistoryCommand();
        clearHistoryCommand.setData(new ModelManager(), history, undoRedoStack);
    }

    @Test
    public void execute() throws Exception {
        history.add("command1");
        history.add("command2");
        undoRedoStack.push(new DummyCommand());
        undoRedoStack.push(new DummyUndoableCommand());
        clearHistoryCommand.execute();
        assertEquals(Collections.emptyList(), history.getHistory());
        assertFalse(undoRedoStack.canUndo());
        assertFalse(undoRedoStack.canRedo());
    }

    class DummyCommand extends Command {
        @Override
        public CommandResult execute() {
            return new CommandResult("");
        }
    }

    class DummyUndoableCommand extends UndoableCommand {
        @Override
        public CommandResult executeUndoableCommand() {
            return new CommandResult("");
        }
    }
}
