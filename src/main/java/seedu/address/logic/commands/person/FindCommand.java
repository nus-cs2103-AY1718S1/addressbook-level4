package seedu.address.logic.commands.person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ListSizeEvent;
import seedu.address.commons.events.ui.ToggleListAllStyleEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.PersonHasKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonHasKeywordsPredicate predicate;

    public FindCommand(PersonHasKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author aziziazfar
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleListAllStyleEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
