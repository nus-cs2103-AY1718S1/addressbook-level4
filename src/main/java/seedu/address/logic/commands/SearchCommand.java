package seedu.address.logic.commands;

import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Searches and lists all persons in address book whose name and DateOfBirth matches the argument keywords.
 * Keyword matching is case insensitive.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name and Date Of Birth "
            + "contain any of the specified keyowrds and displays them as a list with index number. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice 13.10.1997";

    private final SearchContainsKeywordsPredicate predicate;

    public SearchCommand(SearchContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchCommand
                && this.predicate.equals(((SearchCommand) other).predicate));

    }

}
