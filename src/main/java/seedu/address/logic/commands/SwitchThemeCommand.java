package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SwitchThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author zhangshuoyang
/**
 * Switch the theme of the address book
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switch to selected theme\n"
            + "1. Dark;   2. Light;   3. Ugly\n"
            + "Parameters: INDEX (must be 1, 2 or 3)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated: %1$s";

    private final Index index;

    public SwitchThemeCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String[] themeArr = {"Dark", "Light", "Ugly"};
        if (index.getZeroBased() >= themeArr.length || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }

        EventsCenter.getInstance().post(new SwitchThemeRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, themeArr[index.getZeroBased()]));
    }


    @Override
    public boolean equals(Object other) {
        return other == this //shortcut if same object
                || (other instanceof SwitchThemeCommand
                && this.index.equals(((SwitchThemeCommand) other).index));
    }
}
