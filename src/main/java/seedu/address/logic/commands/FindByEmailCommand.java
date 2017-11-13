package seedu.address.logic.commands;

import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose emails matches with any of the keywords.
 * Keyword matching is case sensitive.
 */
public class FindByEmailCommand extends Command {

    public static final String COMMAND_WORD = "findByEmail";
    public static final String COMMAND_ALIAS = "fbe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose emails contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "lettuce@gmail.com";

    private final EmailContainsKeywordsPredicate predicate;

    public FindByEmailCommand(EmailContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByEmailCommand // instanceof handles nulls
                && this.predicate.equals(((FindByEmailCommand) other).predicate)); // state check
    }
}
