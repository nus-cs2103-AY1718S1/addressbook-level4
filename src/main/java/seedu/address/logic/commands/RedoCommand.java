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
    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + " OR "
            + COMMAND_WORDVAR_3
            + ": Redo the number of commands identified by the given number. If no number is given, redo previous"
            + " command only. \n"
            + "Parameters: NUMBER (must be a positive integer) \n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " \n"
            + "Example 2: " + COMMAND_WORDVAR_3 + " 3";

    //@@author vivekscl
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
        for (int i = 1; i <= numberOfCommands; i++) {
            if (!undoRedoStack.canRedo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            undoRedoStack.popRedo().redo();
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
