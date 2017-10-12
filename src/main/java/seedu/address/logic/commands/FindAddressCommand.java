package seedu.address.logic.commands;

import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose address contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAddressCommand extends Command {

    public static final String COMMAND_WORD = "address";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose address contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " pasir ris";

    private final AddressContainsKeywordsPredicate predicate;

    public FindAddressCommand(AddressContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindAddressCommand // instanceof handles nulls
                && this.predicate.equals(((FindAddressCommand) other).predicate)); // state check
    }
}
