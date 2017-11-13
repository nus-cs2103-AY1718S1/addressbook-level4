package seedu.address.logic.commands;

import seedu.address.model.person.FilterKeywordsPredicate;

//@@author hansiang93
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with any attributes that "
            + "matches all the keywords entered by user and displays them as a list with index numbers.\n"
            + "Parameters: [keyword]...\n"
            + "Example: " + COMMAND_WORD + " neighbours friends John";

    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {keyword}";

    private final FilterKeywordsPredicate predicate;

    public FilterCommand(FilterKeywordsPredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
