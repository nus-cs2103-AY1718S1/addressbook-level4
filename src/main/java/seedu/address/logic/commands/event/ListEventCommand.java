package seedu.address.logic.commands.event;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.commons.events.ui.SwitchToEventsListEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listE";
    public static final String COMMAND_ALIAS = "lE";

    public static final String MESSAGE_EVENT_SUCCESS = "Listed all events";

    public ListEventCommand() {
        eventsCenter.registerHandler(this);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        raise(new SwitchToEventsListEvent());
        return new CommandResult(MESSAGE_EVENT_SUCCESS);
    }
}
