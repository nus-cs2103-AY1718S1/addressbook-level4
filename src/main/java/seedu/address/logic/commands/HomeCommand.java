package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;

// @@author johnweikangong
/**
 * Shows home panel of the App.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";
    public static final String COMMAND_ALIAS = "ho";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns you back to home.\n"
            + "Example: " + COMMAND_WORD;
    public static final String HOME_PANEL_REQUEST_EVENT = "HomePanel";

    public static final String MESSAGE_SUCCESS = "You are back at ho-ho-home!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeInformationPanelRequestEvent(HOME_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
