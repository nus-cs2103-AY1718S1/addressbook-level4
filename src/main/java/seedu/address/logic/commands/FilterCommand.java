package seedu.address.logic.commands;

import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;

//@@author chuaweiwen
/**
 * Finds and lists all persons in address book whose name and/or tags contains all of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose names and tags contain "
            + "all of the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [t/TAG]...\n"
            + "Note: At least one of the parameters must be specified.\n"
            + "Example: " + COMMAND_WORD + " n/Alex t/friends";

    private final NameAndTagsContainsKeywordsPredicate predicate;

    public FilterCommand(NameAndTagsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.showDefaultPanel();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
//@@author
