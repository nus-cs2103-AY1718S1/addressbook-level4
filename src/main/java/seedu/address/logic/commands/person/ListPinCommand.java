package seedu.address.logic.commands.person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleListPinStyleEvent;
import seedu.address.commons.events.ui.ToggleSearchBoxStyle;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.PersonIsPinnedPredicate;
//@@author Alim95

/**
 * Lists all pinned persons in the address book to the user.
 */
public class ListPinCommand extends Command {

    public static final String COMMAND_WORD = "listpin";

    public static final String MESSAGE_SUCCESS = "Listed all pinned person";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(new PersonIsPinnedPredicate());
        EventsCenter.getInstance().post(new ToggleListPinStyleEvent());
        EventsCenter.getInstance().post(new ToggleSearchBoxStyle(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
