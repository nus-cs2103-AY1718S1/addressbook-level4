package seedu.address.logic.commands;

import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {
    //@@author adileyzekmoon
    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
