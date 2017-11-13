package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;

// @@author johnweikangong
/**
 * Shows help panel of the App.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_ALIAS = "sos";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    public static final String HELP_PANEL_REQUEST_EVENT = "HelpPanel";

    public static final String MESSAGE_SUCCESS = "Help is here!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeInformationPanelRequestEvent(HELP_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
