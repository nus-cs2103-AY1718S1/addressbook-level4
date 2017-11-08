package seedu.address.logic.commands;

import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose emails matches with any of the keywords.
 * Keyword matching is case sensitive.
 */
public class FindByTagCommand extends Command {

    public static final String COMMAND_WORD = "findByTag";
    public static final String COMMAND_ALIAS = "fbt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who has tags of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "friends";

    private final TagContainsKeywordsPredicate predicate;

    public FindByTagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindByTagCommand) other).predicate)); // state check
    }
}
