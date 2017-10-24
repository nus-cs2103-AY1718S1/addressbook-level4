package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsAllKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose parameters  contains all of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterAllCommand extends Command {

    public static final String COMMAND_WORD = "filterall";
    public static final String COMMAND_ALIAS = "fa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonContainsAllKeywordsPredicate predicate;

    public FilterAllCommand(PersonContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterAllCommand // instanceof handles nulls
                && this.predicate.equals(((FilterAllCommand) other).predicate)); // state check
    }
}
