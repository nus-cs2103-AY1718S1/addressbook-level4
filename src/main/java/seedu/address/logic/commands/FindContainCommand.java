package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsLettersPredicate;

/**
 * Find a person whose name / phone / email / address / tag contains given string
 */
public class FindContainCommand extends Command {

    public static final String COMMAND_WORD = "find_contain";
    public static final String MESSAGE_USAGE = "do later";

    private final PersonContainsLettersPredicate predicate;

    public FindContainCommand(PersonContainsLettersPredicate predicate) {
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
                || (other instanceof FindContainCommand // instanceof handles nulls
                && this.predicate.equals(((FindContainCommand) other).predicate)); // state check
    }
}
