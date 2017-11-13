//@@author keloysiusmak
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ViewAliasRequestEvent;

/**
 * View aliases in a window
 */
public class ViewAliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "viewalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all aliases.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Opened alias window.";

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        EventsCenter.getInstance().post(new ViewAliasRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
