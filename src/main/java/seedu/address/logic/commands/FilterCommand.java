package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ContainsTagsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the current list with persons who are tagged with any of "
            + "the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " family friends colleagues";

    private final ContainsTagsPredicate predicate;

    public FilterCommand(ContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
