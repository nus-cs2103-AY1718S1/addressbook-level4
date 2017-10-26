//@@author A0162268B
package seedu.address.logic.commands.event;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

/**
 * @@reginleiff Finds and lists all events in Sales Navigator which title contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "eventfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice birthday";

    private final TitleContainsKeywordsPredicate predicate;

    public FindEventCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(predicate);
        return new CommandResult(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && this.predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}
