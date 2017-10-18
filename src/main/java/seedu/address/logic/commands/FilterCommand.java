package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ContainsTagsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Filters the current list with persons who are tagged with any of the specified tags.
 * Tag matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the current list with persons who are tagged"
            + " with any of the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " family friends colleagues";

    private final ContainsTagsPredicate predicate;

    public FilterCommand(ContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        Predicate<? super ReadOnlyPerson>  currentPredicate = model.getPersonListPredicate();
        if (currentPredicate == null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(predicate.and(currentPredicate));
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
