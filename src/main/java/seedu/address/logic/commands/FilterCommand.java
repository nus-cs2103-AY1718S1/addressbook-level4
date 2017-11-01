package seedu.address.logic.commands;

//@@author LeeYingZheng
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORDVAR = "filter";


    public static final String MESSAGE_USAGE = COMMAND_WORDVAR
            + ": Filters all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers."
            + " Command is case-insensitive. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORDVAR + " friend colleague \n";

    private final TagContainsKeywordsPredicate predicate;

    public FilterCommand(TagContainsKeywordsPredicate predicate) {
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
