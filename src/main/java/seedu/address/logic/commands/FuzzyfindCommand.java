package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsSubstringsPredicate;

//@@author bokwoon95
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FuzzyfindCommand extends Command {

    public static final String COMMAND_WORD = "fuzzyfind";
    public static final String COMMAND_ALIAS = "ff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "This behaves just like 'Find' except it will match match substrings instead of just words.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsSubstringsPredicate predicate;

    public FuzzyfindCommand(NameContainsSubstringsPredicate predicate) {
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
                || (other instanceof FuzzyfindCommand // instanceof handles nulls
                && this.predicate.equals(((FuzzyfindCommand) other).predicate)); // state check
    }
}
