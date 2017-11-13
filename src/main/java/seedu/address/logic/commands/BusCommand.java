//@@author jshoung
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBusRequestEvent;

/**
 * Opens bus route map for NUS
 */
public class BusCommand extends Command {

    public static final String COMMAND_WORD = "bus";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows bus routes for NUS.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_BUS_MESSAGE = "Opened bus window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowBusRequestEvent());
        return new CommandResult(SHOWING_BUS_MESSAGE);
    }
}
