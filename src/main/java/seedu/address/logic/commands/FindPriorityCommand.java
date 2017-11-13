//@@author inGall
package seedu.address.logic.commands;

import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;

/**
 * Finds and lists all reminders in address book whose priority contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPriorityCommand extends Command {

    public static final String COMMAND_WORD = "findPriority";
    public static final String COMMAND_ALIAS = "fpr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose priority contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " High Medium ";

    private final PriorityContainsKeywordsPredicate predicate;

    public FindPriorityCommand(PriorityContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredReminderList(predicate);
        return new CommandResult(getMessageForPriorityListShownSummary(model.getFilteredReminderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPriorityCommand // instanceof handles nulls
                && this.predicate.equals(((FindPriorityCommand) other).predicate)); // state check
    }
}
