//@@author jshoung
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMapRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the NUS map.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_MAP_MESSAGE = "Opened map window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMapRequestEvent());
        return new CommandResult(SHOWING_MAP_MESSAGE);
    }
}
