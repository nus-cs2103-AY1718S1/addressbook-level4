package seedu.address.logic.commands;

import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain ALL of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/KEYWORD [MORE_PREFIX/KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice a/Blk 100 Street t/friend";

    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
