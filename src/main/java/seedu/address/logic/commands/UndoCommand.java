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

    public static final String[] COMMAND_WORDS = {"undo", "u", "revert"};
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String FULL_MESSAGE_SUCCESS = "Undo success!\nUndone Command: %1$s";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        //@@author arnollim
        String commandString = undoRedoStack.peekUndo().toString();
        String feedbackToUser = parseUndoCommand(commandString);
        undoRedoStack.popUndo().undo();

        return new CommandResult(feedbackToUser);
        //@@author
    }

    //@@author arnollim
    /**
     * Parses the output command to display the previously undone command
     */
    public static String parseUndoCommand(String commandString) {
        String output = String.format(FULL_MESSAGE_SUCCESS, commandString);
        return output;
    }
    //@@author

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
