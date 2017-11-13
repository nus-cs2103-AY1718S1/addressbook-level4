package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AccessCountDisplayToggleEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author Zzmobie
/**
 * Toggles the display of the access count.
 */
public class ToggleAccessDisplayCommand extends Command {
    public static final String COMMAND_WORD = "accessdisplay";
    public static final String COMMAND_ALIAS = "ad";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles the display of the access count. "
            + "Parameters: TYPE (either \"on\" or \"off\")\n"
            + "Example: " + COMMAND_WORD + " on";
    public static final String MESSAGE_SUCCESS = "Display toggled ";

    public static final String TYPE_ON = "on";
    public static final String TYPE_OFF = "off";

    private boolean isDisplayed;

    public ToggleAccessDisplayCommand (boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new AccessCountDisplayToggleEvent(isDisplayed));
        return new CommandResult(MESSAGE_SUCCESS + (isDisplayed ? TYPE_ON : TYPE_OFF) + ". ");
    }
}
