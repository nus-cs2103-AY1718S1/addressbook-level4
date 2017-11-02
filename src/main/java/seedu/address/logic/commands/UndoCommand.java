//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
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
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes undoable commands specified by the number of steps.\n"
            + "Parameters: [STEPS]\n"
            + "Eample: to undo 3 undoable commands:\n"
            + COMMAND_WORD + " 3\n"
            + "Alternate usage: use keyword \"all\" to undo all commands in current session\n"
            + "Eample: " + COMMAND_WORD + " all";


    // default undo one command
    private int steps = 1;

    public UndoCommand(){}

    public UndoCommand(int steps) {
        this.steps = steps;
    }


    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        int occurence = 0;
        while (steps > 0) {
            if (!undoRedoStack.canUndo()) {
                if (occurence == 0) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_FAILURE);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS);
                }
            }
            undoRedoStack.popUndo().undo();
            steps--;
            occurence++;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
//@@author
