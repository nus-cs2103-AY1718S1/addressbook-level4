package seedu.address.logic.commands;

/**
 * Sort and list out all the contacts in the Address Book
 * Order is ascending, from W to A
 */
public class SortAscendingCommand extends Command {

    public final static String COMMAND_WORD = "sortascend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "List all persons by name "
            + "in ascending alphabetical order.\n ";

    @Override
    public CommandResult execute() {
        model.sortNameAscend();
        return new CommandResult("List is now arranged in ascending order.\n");
    }
}
