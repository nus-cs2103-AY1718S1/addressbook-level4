package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listE";
    public static final String COMMAND_ALIAS = "lE";

    public static final String MESSAGE_EVENT_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute() {
        model.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_EVENT_SUCCESS);
    }
}

