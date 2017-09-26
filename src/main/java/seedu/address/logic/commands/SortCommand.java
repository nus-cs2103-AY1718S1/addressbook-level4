package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "List has been sorted.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by name, phone, email or address \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example for name sort: " + COMMAND_WORD + " name\n"
            + "Example for phone sort: " + COMMAND_WORD + " phone";

    private final String toSort;

    public SortCommand(String toSort) {
        this.toSort = toSort;
    }

    @Override
    public CommandResult execute() {
        model.sortList(toSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
