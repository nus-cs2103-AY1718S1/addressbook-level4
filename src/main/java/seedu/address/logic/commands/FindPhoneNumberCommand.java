package seedu.address.logic.commands;

import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPhoneNumberCommand extends Command {

    public static final String COMMAND_WORD = "findphone";
    public static final String COMMAND_ALIAS = "fp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone number contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 98765432";

    private final NumberContainsKeywordsPredicate predicate;

    public FindPhoneNumberCommand(NumberContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindPhoneNumberCommand // instanceof handles nulls
                && this.predicate.equals(((FindPhoneNumberCommand) other).predicate)); // state check
    }
}
