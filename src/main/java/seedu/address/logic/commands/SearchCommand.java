package seedu.address.logic.commands;

import seedu.address.model.person.NamePhoneTagContainsKeywordsPredicate;
//@@author willxujun
/**
 * Finds and lists all persons in address book whose name, phone or tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String MESSAGE_USAGE = ": Finds all persons whose names, phones or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n";

    private final NamePhoneTagContainsKeywordsPredicate predicate;

    public SearchCommand(NamePhoneTagContainsKeywordsPredicate predicate) {
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
