//@@author WangJieee
package seedu.address.logic.commands;

import seedu.address.model.person.PersonHasTagPredicate;

/**
 * Filters and lists all persons in address book whose tag list contains any of the argument tags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag lists contain any of "
            + "the specified tags(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends families";

    private final PersonHasTagPredicate predicate;

    public FilterCommand(PersonHasTagPredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
