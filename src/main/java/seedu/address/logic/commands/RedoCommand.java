package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS = "r";

    //@@author aaronyhsoh
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the most recent N undo commands, where (N = to the INDEX entered)\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";
    //@@author

    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    //@@author aaronyhsoh
    private final Index index;

    public RedoCommand() {
        index = null;
    }

    public RedoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        if (index == null) {
            undoRedoStack.popRedo().redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            int commandsToRedo = index.getOneBased();
            while (commandsToRedo != 0 && undoRedoStack.canRedo()) {
                undoRedoStack.popRedo().redo();
                commandsToRedo -= 1;
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    //@@author
    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }

    //@@author aaronyhsoh
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.index.equals(((RedoCommand) other).index)); // state check
    }
}
