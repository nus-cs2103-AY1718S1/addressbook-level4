package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowAboutRequestEvent;

//@@author yewshengkai
/**
 * Format full help instructions for every command for display.
 */
public class AboutCommand extends Command {

    public static final String COMMAND_WORD = "about";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows iungo developers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_ABOUT_MESSAGE = "Opened about window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowAboutRequestEvent());
        return new CommandResult(SHOWING_ABOUT_MESSAGE);
    }
}
