package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds people in the address book.
 */
public class FindPersonCommand extends FindCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    private final NameContainsKeywordsPredicate personPredicate;

    public FindPersonCommand(NameContainsKeywordsPredicate predicate) {
        this.personPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        assert(personPredicate != null);
        model.updateFilteredPersonList(personPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof FindPersonCommand
                && this.personPredicate.equals(((FindPersonCommand) other).personPredicate);
    }
}
