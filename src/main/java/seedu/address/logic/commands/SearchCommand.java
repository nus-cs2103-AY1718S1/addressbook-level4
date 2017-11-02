package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Searches the address book for any parameters that match the given keyword.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "se";

    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " KEYWORDS";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "bernice serangoon";

    private final PersonContainsKeywordsPredicate predicate;

    public SearchCommand(PersonContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
