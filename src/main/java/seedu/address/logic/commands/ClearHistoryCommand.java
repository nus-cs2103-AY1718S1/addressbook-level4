package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the command history and the undo/redo stack.
 */
public class ClearHistoryCommand extends Command {

    public static final String COMMAND_WORD = "clearhistory";
    public static final String COMMAND_ALIAS = "ch";
    public static final String MESSAGE_SUCCESS = "Command history cleared.";

    @Override
    public CommandResult execute() throws CommandException {
        undoRedoStack.clear();
        history.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        super.setData(model, history, undoRedoStack);
        this.history = history;
        this.undoRedoStack = undoRedoStack;
    }
}
