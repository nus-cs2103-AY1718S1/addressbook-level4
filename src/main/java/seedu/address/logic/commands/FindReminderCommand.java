package seedu.address.logic.commands;

import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Finds and lists all reminders in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindReminderCommand extends Command {

    public static final String COMMAND_WORD = "findReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final TaskContainsKeywordsPredicate predicate;

    public FindReminderCommand(TaskContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredReminderList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredReminderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindReminderCommand // instanceof handles nulls
                && this.predicate.equals(((FindReminderCommand) other).predicate)); // state check
    }
}
