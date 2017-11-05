package seedu.address.logic.commands.person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleListPinStyleEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.PersonHasKeywordsPredicate;

//@@author Alim95
/**
 * Finds and lists all pinned persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPinnedCommand extends Command {

    public static final String COMMAND_WORD = "findpinned";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pinned persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonHasKeywordsPredicate predicate;

    public FindPinnedCommand(PersonHasKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new ToggleListPinStyleEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPinnedCommand // instanceof handles nulls
                && this.predicate.equals(((FindPinnedCommand) other).predicate)); // state check
    }
}
