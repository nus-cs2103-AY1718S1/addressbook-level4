package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsPartialKeywordsPredicate;

//@@author RSJunior37
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords partially.
 * Keyword matching is case sensitive.
 */
public class PartialFindCommand extends Command {

    public static final String[] COMMAND_WORDS = {"pfind", "pf", "plook", "plookup"};
    public static final String COMMAND_WORD = "pfind";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Finds all persons whose names starts with "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Ali Bo Ch";

    private final NameContainsPartialKeywordsPredicate predicate;

    public PartialFindCommand (NameContainsPartialKeywordsPredicate predicate) {
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
                || (other instanceof PartialFindCommand // instanceof handles nulls
                && this.predicate.equals(((PartialFindCommand) other).predicate)); // state check
    }
}
