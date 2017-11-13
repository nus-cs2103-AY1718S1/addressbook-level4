package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BrowserAndRemindersPanelToggleEvent;

//@@author justinpoh
/**
 * Toggles between the browser and reminders.
 */

public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";
    public static final String MESSAGE_TOGGLE_SUCCESS = "Toggle successful.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new BrowserAndRemindersPanelToggleEvent());
        return new CommandResult(MESSAGE_TOGGLE_SUCCESS);
    }


}
