package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AccessWebsiteRequestEvent;
import seedu.address.commons.events.ui.PersonPanelUnselectEvent;
import seedu.address.commons.events.ui.TogglePanelEvent;

//@@author itsdickson
/**
 * Lists all events in the address book to the user.
 */
public class EventsCommand extends Command {

    public static final String COMMAND_WORD = "events";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of events.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EVENTS_MESSAGE = "Listed all events.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new TogglePanelEvent(COMMAND_WORD));
        EventsCenter.getInstance().post(new PersonPanelUnselectEvent());
        EventsCenter.getInstance().post(new AccessWebsiteRequestEvent("https://maps.google.com/"));
        return new CommandResult(SHOWING_EVENTS_MESSAGE);
    }
}
//@@author
