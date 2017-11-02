package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;

/**
 * Adds a person to the resident book.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "srt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the displayed resident book. "
            + "Parameters: "
            + "Sorting-Criteria" + " "
            + "Example: " + COMMAND_WORD + " "
            + "name";

    public static final String MESSAGE_SUCCESS = "Sorted resident book by: %1$s";
    public static final String MESSAGE_FAILURE = "Resident book already sorted by: %1$s";

    private final String sortCriteria;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public SortCommand(String sortBy) {
        this.sortCriteria = sortBy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.sortBy(sortCriteria);
            return new CommandResult(String.format(MESSAGE_SUCCESS, sortCriteria));
        } catch (AlreadySortedException e) {
            String failureMessage = String.format(MESSAGE_FAILURE, sortCriteria);
            throw new CommandException(failureMessage);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortCriteria.equals(((SortCommand) other).sortCriteria));
    }
}
