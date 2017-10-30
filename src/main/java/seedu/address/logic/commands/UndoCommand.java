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
    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " OR "
            + COMMAND_WORDVAR_3
            + ": Undo the number of commands identified by the given number. If no number is given, undo previous"
            + " command only. \n"
            + "Parameters: NUMBER (must be a positive integer) \n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " \n"
            + "Example 2: " + COMMAND_WORDVAR_3 + " 2";

    //@@author vivekscl
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
        for (int i = 1; i <= numberOfCommands; i++) {
            if (!undoRedoStack.canUndo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            undoRedoStack.popUndo().undo();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author
    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }

}
