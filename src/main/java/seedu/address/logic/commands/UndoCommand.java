package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";

    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo Commands identified by the index number used. If no index number, assumed to be 1.\n"
            + "Parameters: (POSITIVE INDEX)\n"
            + "Example: " + COMMAND_WORD + ""
            + "Example: " + COMMAND_WORD + " 1";
    public static final String NUMBER_ONE = "1";
    public static final String MESSAGE_INVALID_COMMAND = "Shouldn't reach here";
    public static final String MESSAGE_EMPTYSTACK = "No more commands to undo!";
    public static final String MESSAGE_TOO_MANY_UNDO = "Maximum undo size: ";

    public static final int INDEX_ZERO = 0;
    public static final int EMPTY_STACK = 0;

    private int numUndo;
    //@@author justintkj
    public UndoCommand(int numUndo) {
        this.numUndo = numUndo;
    }

    public UndoCommand() throws IllegalValueException {
        numUndo = ParserUtil.parseNumber(NUMBER_ONE);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        checkStackNotEmpty();
        checkUndoSizeNotBiggerThanStack();

        undoMultipleTimes();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * Remove commands for numUndo number of times
     *
     * @throws CommandException if undo while stack is empty
     */
    private void undoMultipleTimes() throws CommandException {
        for (int i = INDEX_ZERO; i < numUndo; i++) {
            if (!undoRedoStack.canUndo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            undoRedoStack.popUndo().undo();
        }
    }

    /**
     * Checks if number of undos is not bigger than current avaliable number of undo
     *
     * @throws CommandException if redo while stack is empty
     */
    private void checkUndoSizeNotBiggerThanStack() throws CommandException {
        if (numUndo > undoRedoStack.getUndoStackSize()) {
            throw new CommandException(MESSAGE_TOO_MANY_UNDO + undoRedoStack.getUndoStackSize());
        }
    }

    /**
     * Checks if current number of redo avaliable is zero
     *
     * @throws CommandException if current stack size is zero
     */
    private void checkStackNotEmpty() throws CommandException {
        if (undoRedoStack.getUndoStackSize() == EMPTY_STACK) {
            throw new CommandException(MESSAGE_EMPTYSTACK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.numUndo == (((UndoCommand) other).numUndo)); // state check
    }

    //@@author
    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
