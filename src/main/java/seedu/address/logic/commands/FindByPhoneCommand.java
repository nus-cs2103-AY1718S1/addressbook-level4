package seedu.address.logic.commands;

import seedu.address.model.person.PhoneContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose phone number contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByPhoneCommand extends Command {

    public static final String COMMAND_WORD = "findByPhone";
    public static final String COMMAND_ALIAS = "fbp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "91234567";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindByPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByPhoneCommand // instanceof handles nulls
                && this.predicate.equals(((FindByPhoneCommand) other).predicate)); // state check
    }
}
