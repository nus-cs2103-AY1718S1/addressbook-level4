package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Finds and lists all persons in address book who has a tag that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPersonsWithTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";
    public static final String COMMAND_WORD_ALIAS1 = "findtag";
    public static final String COMMAND_WORD_ALIAS2 = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose has tags containing any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " classmates colleagues";

    private final PersonContainsTagsPredicate predicate;

    public FindPersonsWithTagsCommand(PersonContainsTagsPredicate predicate) {
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
                || (other instanceof FindPersonsWithTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindPersonsWithTagsCommand) other).predicate)); // state check
    }
}
