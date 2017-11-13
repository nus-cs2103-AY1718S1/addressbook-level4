package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS = "r";

    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo Commands identified by the index number used. If no index number, assumed to be 1.\n"
            + "Parameters: (POSITIVE INDEX)\n"
            + "Example: " + COMMAND_WORD + ""
            + "Example: " + COMMAND_WORD + " 1";
    public static final String TOO_MANY_REDO_FORMAT = "Maximum redo size: ";
    public static final String EMPTY_STACK_ERROR_MESSAGE = "No more commands to redo!";
    public static final int INDEX_ZERO = 0;
    public static final String INDEX_ONE = "1";
    public static final int EMPTY_STACK = 0;

    private int numRedo;
    //@@author justintkj
    public RedoCommand(int numRedo) {
        requireNonNull(numRedo);
        this.numRedo = numRedo;
    }
    public RedoCommand() throws IllegalValueException {
        this.numRedo = ParserUtil.parseNumber(INDEX_ONE);
    }
    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        checksStackNotEmpty();
        checksRedoSizeNotBiggerThanStack();

        redoMultipleTimes();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Repeats numRedo commands number of times
     *
     * @throws CommandException if redo while stack is empty
     */
    private void redoMultipleTimes() throws CommandException {
        for (int i = INDEX_ZERO; i < numRedo; i++) {
            if (!undoRedoStack.canRedo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            undoRedoStack.popRedo().redo();
        }
    }

    /**
     * Checks if number of redos is not bigger than current avaliable number of redos
     *
     * @throws CommandException if redo while stack is empty
     */
    private void checksRedoSizeNotBiggerThanStack() throws CommandException {
        if (numRedo > undoRedoStack.getRedoStackSize()) {
            throw new CommandException(TOO_MANY_REDO_FORMAT + undoRedoStack.getRedoStackSize());
        }
    }

    /**
     * Checks if current number of redo avaliable is zero
     *
     * @throws CommandException if current stack size is zero
     */
    private void checksStackNotEmpty() throws CommandException {
        if (undoRedoStack.getRedoStackSize() == EMPTY_STACK) {
            throw new CommandException(EMPTY_STACK_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.numRedo == (((RedoCommand) other).numRedo)); // state check
    }

    //@@author

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
