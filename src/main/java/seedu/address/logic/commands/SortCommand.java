package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author khooroko
/**
 * Sorts the masterlist by the input argument (i.e. "name" or "debt").
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Address book has been sorted by %1$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book by specified ordering in "
            + "intuitive order. If no ordering is specified, the address book is sorted by name.\n"
            + "Parameters: ORDERING (i.e. \"name\", \"debt\", \"deadline\" or \"cluster\")\n"
            + "Example: " + COMMAND_WORD + " debt";
    public static final String DEFAULT_ORDERING = "name";

    private final String order;

    public SortCommand(String order) {
        //validity of order to sort is checked in {@code SortCommandParser}
        if (order.equals("")) {
            order = DEFAULT_ORDERING;
        }
        this.order = order;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.sortBy(order);
        } catch (IllegalArgumentException ive) {
            throw new CommandException(ive.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, order));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand s = (SortCommand) other;
        return order.equals(s.order);
    }
}
