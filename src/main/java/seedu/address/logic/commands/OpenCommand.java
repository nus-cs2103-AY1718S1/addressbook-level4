package seedu.address.logic.commands;

//@@author jeffreygohkw

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.OpenRequestEvent;

/**
 * Opens the data from a desired location
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final String COMMAND_ALIAS = "o";

    public static final String OPEN_COMMAND_SUCCESS = "Successfully opened file.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new OpenRequestEvent());
        return new CommandResult(OPEN_COMMAND_SUCCESS);
    }
}
