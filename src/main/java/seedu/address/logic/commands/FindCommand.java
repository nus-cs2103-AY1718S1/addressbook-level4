package seedu.address.logic.commands;

import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    //@@author Affalen
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (non-case sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/Alice \n"
            + "Example: " + COMMAND_WORD + " p/12345678 \n"
            + "Example: " + COMMAND_WORD + " a/138 Clementi Road \n"
            + "Example: " + COMMAND_WORD + " t/[Friends] \n"
            + "Example: " + COMMAND_WORD + " r/[Likes coffee] \n"
            + "Example: " + COMMAND_WORD + " b/[21-10-1995] \n";

    private Predicate predicate;
    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }
    //@@author
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
