//@@author erik0704
package seedu.address.logic.commands;

import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tags contains all of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds any persons whose tags contain all of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleagues";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
