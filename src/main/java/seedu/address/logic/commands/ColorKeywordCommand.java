package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ColorKeywordEvent;

/**
 * Color the command keywords in the application
 */
public class ColorKeywordCommand extends Command {

    public static final String COMMAND_WORD = "color";
    public static final String MESSAGE_SUCCESS = " highlighting of keyword.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Highlighting the command keywords "
            + "Parameters: enable / disable\n"
            + "Example: " + COMMAND_WORD + " enable";

    public static final String DISABLE_COLOR = "Disable";
    public static final String ENABLE_COLOR = "Enable";

    private final boolean isEnableColor;

    public ColorKeywordCommand(String attributeName) {
        if (attributeName.equalsIgnoreCase("disable")) {
            isEnableColor = false;
        } else {
            isEnableColor = true;
        }
    }

    @Override
    public CommandResult execute() {
        if (isEnableColor) {
            EventsCenter.getInstance().post(new ColorKeywordEvent(true));
            return new CommandResult(ENABLE_COLOR + MESSAGE_SUCCESS);
        }

        EventsCenter.getInstance().post(new ColorKeywordEvent(false));
        return new CommandResult(DISABLE_COLOR + MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ColorKeywordCommand // instanceof handles nulls
                && this.isEnableColor == (((ColorKeywordCommand) other).isEnableColor)); // state check
    }
}
