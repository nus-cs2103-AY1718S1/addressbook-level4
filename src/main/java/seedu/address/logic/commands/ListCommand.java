package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;
import static seedu.address.logic.parser.SortUtil.MESSAGE_SORT_USAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.parser.SortArgument;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":"
            + "Displays all persons in the address book, "
            + "sorted by the specified sort order or the last known sort order." + "\n"
            + "Parameters: " + MESSAGE_SORT_USAGE + "\n"
            + "Example: " + COMMAND_WORD + " "
            + SORT_ARGUMENT_NAME_DEFAULT +  " " + SORT_ARGUMENT_PHONE_DESCENDING;

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private final List<SortArgument> sortArguments;

    public ListCommand(List<SortArgument> sortArguments) {
        this.sortArguments = sortArguments;
    }

    @Override
    public CommandResult execute() {
        model.updateSortComparator(sortArguments);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
