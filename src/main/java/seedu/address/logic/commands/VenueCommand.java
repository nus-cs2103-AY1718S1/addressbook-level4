//@@author jshoung
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.VenueRequestEvent;

/**
 *
 * Opens NUSMods webpage to get venue information.
 *
 */
public class VenueCommand extends Command {
    public static final String COMMAND_WORD = "venue";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows venue occupancy information.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " COM1-0114" + "\n"
            + "Example: " + COMMAND_ALIAS + " COM1-0114";

    public static final String SHOWING_VENUE_MESSAGE = "Loaded venue information for: ";

    private final String venue;

    public VenueCommand(String venue) {
        this.venue = venue;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new VenueRequestEvent(venue));
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        return new CommandResult(SHOWING_VENUE_MESSAGE + venue);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VenueCommand // instanceof handles nulls
                && this.venue.equals(((VenueCommand) other).venue)); // state check
    }
}
//@@author
