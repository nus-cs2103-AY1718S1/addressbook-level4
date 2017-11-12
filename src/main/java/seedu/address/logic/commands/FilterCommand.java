package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose parameters contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {
    //@@author adileyzekmoon
    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "fi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonContainsKeywordsPredicate predicate;

    public FilterCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
