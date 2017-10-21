package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PARCELS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowParcelListEvent;

/**
 * Lists all parcels in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all parcels";


    @Override
    public CommandResult execute() {
        model.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        EventsCenter.getInstance().post(new ShowParcelListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
