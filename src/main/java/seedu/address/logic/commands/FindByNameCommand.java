package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsKeywordsPredicate;
/**
 * Finds and lists all persons in name book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByNameCommand extends Command {

    public static final String COMMAND_WORD = "findByName";
    public static final String COMMAND_ALIAS = "fbn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "Amy";

    private final NameContainsKeywordsPredicate predicate;

    public FindByNameCommand(NameContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByNameCommand // instanceof handles nulls
                && this.predicate.equals(((FindByNameCommand) other).predicate)); // state check
    }
}
