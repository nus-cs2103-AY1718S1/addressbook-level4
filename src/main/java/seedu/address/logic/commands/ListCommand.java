package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;
import static seedu.address.logic.parser.SortUtil.MESSAGE_SORT_USAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.parser.SortArgument;

/**
 * Lists all persons in the rolodex to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "l", "show", "display"));
    public static final String COMMAND_HOTKEY = "Ctrl+L";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Displays all persons in the rolodex, "
            + "sorted by the specified sort order or the default sort order." + "\n"
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
