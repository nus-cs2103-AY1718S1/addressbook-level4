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

    //@@author LeeYingZheng
    public static final String COMMAND_WORDVAR_1 = "redo";
    public static final String COMMAND_WORDVAR_2 = "r";
    //@@author
    public static final String COMMAND_WORDVAR_3 = "redomult";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    //@@author vivekscl
    public static final String MESSAGE_SOME_COMMANDS_REDONE = "There were only %1$s commands to redo. "
            + "Cannot redo %2$s more commands!";
    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " OR "
            + COMMAND_WORDVAR_3
            + ": Redo the number of commands identified by the given number. If "
            + COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " is used, only the previous command will be redone. \n"
            + "Parameters: NUMBER (must be a positive integer) if "
            + COMMAND_WORDVAR_3
            + " is used.\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " \n"
            + "Example 2: " + COMMAND_WORDVAR_3 + " 3";

    private final int numberOfCommands;

    public RedoCommand() {
        this(1);
    }

    public RedoCommand(int numberOfCommands) {
        this.numberOfCommands = numberOfCommands;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (numberOfCommands == 1) {
            redoHandler(MESSAGE_FAILURE);
            undoRedoStack.popRedo().redo();
        } else if (numberOfCommands > 1) {
            for (int i = 0; i < numberOfCommands; i++) {
                int numberOfCommandsUndone = i;
                int numberOfCommandsLeft = numberOfCommands - i;
                redoHandler(String.format(MESSAGE_SOME_COMMANDS_REDONE, numberOfCommandsUndone, numberOfCommandsLeft));
                undoRedoStack.popRedo().redo();
            }
        } else {
            assert false : "Number of commands must be at least 1";
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Handles the case where there is no command left to redo and outputs the corresponding message.
     */
    private void redoHandler(String message) throws CommandException {
        if (!undoRedoStack.canRedo()) {
            throw new CommandException(message);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.numberOfCommands == ((RedoCommand) other).numberOfCommands); // state check
    }

    //@@author
    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
