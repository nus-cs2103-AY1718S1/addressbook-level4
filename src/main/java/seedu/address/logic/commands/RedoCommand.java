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

    public static final String[] COMMAND_WORDS = {"redo", "r"};
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String FULL_MESSAGE_SUCCESS = "Redo success!\nRedone Command: %1$s";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String commandString = undoRedoStack.peekRedo().toString();
        String feedbackToUser = parseCommand(commandString);
        undoRedoStack.popRedo().redo();
        return new CommandResult(feedbackToUser);
    }

    /**
     * Parses the output command to display the previously undone command
     */
    public static String parseCommand(String commandString) {
        String output = String.format(FULL_MESSAGE_SUCCESS, commandString);
        return output;
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
