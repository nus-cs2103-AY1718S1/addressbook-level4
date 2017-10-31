package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo a number of commands.\n"
            + "Parameters: NUMBER_OF_COMMANDS_TO_REDO (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    // @@author donjar
    private final int amount;

    public RedoCommand(int amount) {
        this.amount = amount;
    }
    // @@author

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        // @@author donjar
        int commandsRedoed = 0;
        while (undoRedoStack.canRedo() && commandsRedoed < amount) {
            undoRedoStack.popRedo().redo();
            commandsRedoed++;
        }
        return new CommandResult(getSuccessMessage(commandsRedoed));
        // @@author
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }

    // @@author donjar
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.amount == ((RedoCommand) other).amount); // state check
    }

    /**
     * Constructs the success message from the given amount. This takes into account that the number of commands
     * undone influences the singular/plural form of "command".
     */
    public static String getSuccessMessage(int amount) {
        if (amount == 1) {
            return "1 command redoed.";
        } else {
            return String.format("%1$s commands redoed.", amount);
        }
    }
    // @@author
}
