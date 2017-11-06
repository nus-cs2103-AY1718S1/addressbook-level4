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
    public static final String MESSAGE_SUCCESS_FULL = "Undo success!\nUndone Command: %1$s";
    public static String MESSAGE_OUTPUT = "";//To be completed before output
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        MESSAGE_OUTPUT = parseCommand(undoRedoStack);
        undoRedoStack.popUndo().undo();
        //return new CommandResult(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_OUTPUT);
    }

    public String parseCommand(UndoRedoStack undoRedoStack){
        String commandString = undoRedoStack.peekUndo().toString();
        String output = String.format(MESSAGE_SUCCESS_FULL, commandString);
        return output;
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
