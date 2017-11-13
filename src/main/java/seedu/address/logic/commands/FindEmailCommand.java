//@@author inGall
package seedu.address.logic.commands;

import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose email contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindEmailCommand extends Command {

    public static final String COMMAND_WORD = "findEmail";
    public static final String COMMAND_ALIAS = "fe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose emails contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " test@example.com";

    private final EmailContainsKeywordsPredicate predicate;

    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForEmailListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEmailCommand // instanceof handles nulls
                && this.predicate.equals(((FindEmailCommand) other).predicate)); // state check
    }
}
