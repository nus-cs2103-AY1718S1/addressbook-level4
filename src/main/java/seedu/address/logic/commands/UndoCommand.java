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

    public static final String COMMAND_WORDVAR_1 = "undo";
    public static final String COMMAND_WORDVAR_2 = "u";
    public static final String COMMAND_WORDVAR_3 = "undomult";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    //@@author vivekscl
    public static final String MESSAGE_SOME_COMMANDS_UNDONE = "There were only %1$s commands to undo. "
            + "Cannot undo %2$s more commands!";
    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " OR "
            + COMMAND_WORDVAR_3
            + ": Undo the number of commands identified by the given number. If "
            + COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " is used, only the previous command will be undone. \n"
            + "Parameters: NUMBER (must be a positive integer) if "
            + COMMAND_WORDVAR_3
            + " is used.\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " \n"
            + "Example 2: " + COMMAND_WORDVAR_3 + " 2";

    private final int numberOfCommands;

    public UndoCommand() {
        this(1);
    }

    public UndoCommand(int numberOfCommands) {
        this.numberOfCommands = numberOfCommands;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (numberOfCommands == 1) {
            undoHandler(MESSAGE_FAILURE);
            undoRedoStack.popUndo().undo();
        } else if (numberOfCommands > 1) {
            for (int i = 0; i < numberOfCommands; i++) {
                int numberOfCommandsUndone = i;
                int numberOfCommandsLeft = numberOfCommands - i;
                undoHandler(String.format(MESSAGE_SOME_COMMANDS_UNDONE, numberOfCommandsUndone, numberOfCommandsLeft));
                undoRedoStack.popUndo().undo();
            }
        } else {
            assert false : "Number of commands must be at least 1";
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Handles the case where there is no command left to undo and outputs the corresponding message.
     */
    private void undoHandler(String message) throws CommandException {
        if (!undoRedoStack.canUndo()) {
            throw new CommandException(message);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.numberOfCommands == ((UndoCommand) other).numberOfCommands); // state check
    }

    //@@author
    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
