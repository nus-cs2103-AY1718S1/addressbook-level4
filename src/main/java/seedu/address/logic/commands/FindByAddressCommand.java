package seedu.address.logic.commands;

import seedu.address.model.person.AddressContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose address contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByAddressCommand extends Command {

    public static final String COMMAND_WORD = "findByAddress";
    public static final String COMMAND_ALIAS = "fba";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose addresses contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "Ang Mo Kio";

    private final AddressContainsKeywordsPredicate predicate;

    public FindByAddressCommand(AddressContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindByAddressCommand // instanceof handles nulls
                && this.predicate.equals(((FindByAddressCommand) other).predicate)); // state check
    }
}
