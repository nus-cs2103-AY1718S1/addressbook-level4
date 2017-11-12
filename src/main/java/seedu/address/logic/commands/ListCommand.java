package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AccessWebsiteRequestEvent;
import seedu.address.commons.events.ui.EventPanelUnselectEvent;
import seedu.address.commons.events.ui.TogglePanelEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new TogglePanelEvent(COMMAND_WORD));
        EventsCenter.getInstance().post(new EventPanelUnselectEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
