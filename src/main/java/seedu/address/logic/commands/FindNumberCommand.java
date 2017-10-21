package seedu.address.logic.commands;

import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose number contains any of the argument keywords.
 */

public class FindNumberCommand extends Command {

    public static final String COMMAND_WORD = "findnum";
    public static final String COMMAND_ALIAS = "fin";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 98765432 12345678 61772655";

    private final NumberContainsKeywordsPredicate predicate;

    public FindNumberCommand(NumberContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindNumberCommand // instanceof handles nulls
                && this.predicate.equals(((FindNumberCommand) other).predicate)); // state check
    }
}
