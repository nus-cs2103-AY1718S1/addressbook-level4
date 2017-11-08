package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.UniquePersonList;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the most recent N commands, where (N = to the INDEX entered)\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    private final Index index;

    public UndoCommand() {
        index = null;
    }

    public UndoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (index == null) {
            undoRedoStack.popUndo().undo();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        else {
            int commandsToUndo = index.getOneBased();
            while (commandsToUndo != 0 && undoRedoStack.canUndo()) {
                undoRedoStack.popUndo().undo();
                commandsToUndo -= 1;
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.index.equals(((UndoCommand) other).index)); // state check
    }
}
