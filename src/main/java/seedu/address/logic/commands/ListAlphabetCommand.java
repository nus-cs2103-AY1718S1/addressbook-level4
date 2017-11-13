//@@author majunting
package seedu.address.logic.commands;

import seedu.address.model.person.NameStartsWithAlphabetPredicate;

/**
 * find persons whose name starts with a certain alphabet
 */

public class ListAlphabetCommand extends Command {

    public static final String COMMAND_WORD = "listalp";
    public static final String COMMAND_ALIAS = "la";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names start with "
            + "the specified alphabet (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: Alphabet\n"
            + "Example: " + COMMAND_WORD + "a";

    private final NameStartsWithAlphabetPredicate predicate;

    public ListAlphabetCommand(NameStartsWithAlphabetPredicate predicate) {
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
                || (other instanceof ListAlphabetCommand // instanceof handles nulls
                && this.predicate.equals(((ListAlphabetCommand) other).predicate)); // state check
    }
}
