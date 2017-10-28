package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts the list of contacts in the address book.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of contacts "
            + "by the field specified and displays them as a list. "
            + "Parameters: FIELD\n"
            + "Example: " + COMMAND_WORD + " tag";

    public static final String MESSAGE_SORT_PERSON_SUCCESS = "List sorted successfully!";
    public static final String MESSAGE_INVALID_FIELD = "Field provided is invalid!";

    private final String field;

    public SortCommand(String field) {
        this.field = field.toLowerCase();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        switch (field) {
        case "name":
        case "phone":
        case "email":
        case "address":
        case "tag":
        case "meeting":
            model.sort(field);
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_FIELD);
        }
        return new CommandResult(MESSAGE_SORT_PERSON_SUCCESS);
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
        return field.equals(s.field);
    }
}

