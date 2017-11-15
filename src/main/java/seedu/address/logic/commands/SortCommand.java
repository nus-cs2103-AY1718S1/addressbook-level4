package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.InvalidSortTypeException;

//@@author huiyiiih

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_UNKNOWN_SORT_TYPE = "Sorting type not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list according to either name, tag, "
            + "company, priority, status\n"
            + "Parameters: TYPE  (name, tag, company, priority, status)\n"
            + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "Sorted according to ";
    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.sortPersonList(type);
            return new CommandResult(MESSAGE_SUCCESS + type);
        } catch (InvalidSortTypeException iste) {
            throw new CommandException(MESSAGE_UNKNOWN_SORT_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.type == (((SortCommand) other).type)); // state check
    }
}
//@@author
