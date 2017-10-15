package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DEFAULT;
import static seedu.address.logic.parser.SortUtil.MESSAGE_SORT_USAGE;

import java.util.List;

import seedu.address.logic.parser.SortArgument;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;

/**
 * Finds and lists all persons in rolodex whose name roughly match any of
 * the argument keywords up to code-specified word length limits.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_ABBREV = "f";
    public static final String COMMAND_WORD_ALT = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names roughly match any of "
            + "the specified keywords (case-insensitive) "
            + "or whose tags contains the specified keywords (case-sensitive), "
            + "and displays them as a list with index numbers, "
            + "sorted by the specified sort order or the default sort order.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..." + MESSAGE_SORT_USAGE + "\n"
            + "Example: " + COMMAND_WORD + " alice bobby charlie "
            + SORT_ARGUMENT_PHONE_DEFAULT + " " + SORT_ARGUMENT_NAME_DESCENDING;

    private final PersonDataContainsKeywordsPredicate predicate;
    private final List<SortArgument> sortArguments;

    public FindCommand(PersonDataContainsKeywordsPredicate predicate, List<SortArgument> sortArguments) {
        this.predicate = predicate;
        this.sortArguments = sortArguments;
    }

    @Override
    public CommandResult execute() {
        model.updateSortComparator(sortArguments);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getLatestPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
