// @@author donjar

package seedu.address.logic.commands;

import seedu.address.model.person.NameMatchesRegexPredicate;

/**
 * Finds and lists all persons in address book whose name is matched by the regex given.
 * Keyword matching is case sensitive.
 */
public class FindRegexCommand extends Command {

    public static final String COMMAND_WORD = "findregex";
    public static final String COMMAND_ALIAS = "fr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names match "
            + "the specified regex and displays them as a list with index numbers.\n"
            + "Parameters: REGEX\n"
            + "Example: " + COMMAND_WORD + " ^Joh?n$";

    private final NameMatchesRegexPredicate predicate;

    public FindRegexCommand(NameMatchesRegexPredicate predicate) {
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
                || (other instanceof FindRegexCommand // instanceof handles nulls
                && this.predicate.equals(((FindRegexCommand) other).predicate)); // state check
    }
}
