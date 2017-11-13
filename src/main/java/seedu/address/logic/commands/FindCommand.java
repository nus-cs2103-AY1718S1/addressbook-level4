package seedu.address.logic.commands;

import seedu.address.model.person.FieldContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name or tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags, depending "
            + "on an optional given prefix, contain any of the specified keywords (case-insensitive) and displays "
            + "them as a list, sorted by the position of matched string, with index numbers. Matches name by default.\n"
            + "Parameters: [PREFIX] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/ alice bob charlie";

    private final FieldContainsKeywordsPredicate predicate;


    public FindCommand(FieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.updateSortedFilteredPersonList(predicate.sortOrderComparator());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
