package seedu.address.logic.commands;

import seedu.address.model.person.exceptions.InvalidSortTypeException;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_UNKNOWN_SORT_TYPE = "Sorting type not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list according to either name, tag, "
            + "address\n"
            + "Parameters: TYPE  (must be positive integer range from 1 to 3)\n"
            + "1: Name; 2: Tags; 3: Address\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String[] MESSAGE_SUCCESS = {"Sorted according to name", "Sorted according to tags", "Sorted "
            + " according to address"};
    private final int type;

    public SortCommand(int type) {
        this.type = type;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortPerson(type);
            return new CommandResult(MESSAGE_SUCCESS[type - 1]);
        } catch (InvalidSortTypeException iste) {
            return new CommandResult(MESSAGE_UNKNOWN_SORT_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.type == (((SortCommand) other).type)); // state check
    }
}
