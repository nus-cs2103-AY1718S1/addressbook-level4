package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo a number of commands.\n"
            + "Parameters: NUMBER_OF_COMMANDS_TO_UNDO (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int amount;

    public UndoCommand(int amount) {
        this.amount = amount;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        int commandsUndoed = 0;
        while (undoRedoStack.canUndo() && commandsUndoed < amount) {
            undoRedoStack.popUndo().undo();
            commandsUndoed++;
        }
        return new CommandResult(getSuccessMessage(commandsUndoed));
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
                && this.amount == ((UndoCommand) other).amount); // state check
    }

    /**
     * Constructs the success message from the given amount. This takes into account that the number of commands
     * undone influences the singular/plural form of "command".
     */
    public static String getSuccessMessage(int amount) {
        if (amount == 1) {
            return "1 command undoed.";
        } else {
            return String.format("%1$s commands undoed.", amount);
        }
    }
}
