package seedu.address.logic.commands.person;

import static seedu.address.model.Model.PREDICATE_SHOW_NOT_HIDDEN;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleListAllStyleEvent;
import seedu.address.commons.events.ui.ToggleSearchBoxStyle;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_NOT_HIDDEN);
        EventsCenter.getInstance().post(new ToggleListAllStyleEvent());
        EventsCenter.getInstance().post(new ToggleSearchBoxStyle(false));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
