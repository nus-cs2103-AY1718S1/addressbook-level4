package seedu.address.logic.commands;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
//@@author sidhmads
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String COMMAND_SUCCESS = "Sorted Successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the persons based on their name\n "
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    public SortCommand() { }

    @Override
    public CommandResult execute() {
        model.sortPersons();
        return new CommandResult(COMMAND_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand); // instanceof handles nulls
    }
}
