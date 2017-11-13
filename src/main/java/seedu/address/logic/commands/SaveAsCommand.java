package seedu.address.logic.commands;

//@@author jeffreygohkw

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SaveAsRequestEvent;

/**
 * Saves the data to a new save file at a desired location
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_ALIAS = "sa";

    public static final String SAVE_AS_COMMAND_SUCCESS = "Successfully saved file.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SaveAsRequestEvent());
        return new CommandResult(SAVE_AS_COMMAND_SUCCESS);
    }
}
