package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.UiManager;

import javax.swing.*;

/**
 * Changes theme of UI.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "changetheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme of UI between dark and bright themes.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Theme changed!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
