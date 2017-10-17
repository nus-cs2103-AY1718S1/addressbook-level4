package seedu.address.logic.commands;

import seedu.address.model.tag.NameWithTagContainsKeywordsPredicate;

/**
 * Search a group of Persons identified by the tag entered by users.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_WORD_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search a group of Persons identified by the tag entered by users.\n"
            + "Parameters: TAG (must be a tag exists)\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    private final NameWithTagContainsKeywordsPredicate predicate;

    public SearchCommand(NameWithTagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.updateFilteredPersonByTagList(predicate);
        return new CommandResult(getMessageForPersonList(model.getFilteredPersonByTagList()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }

}
