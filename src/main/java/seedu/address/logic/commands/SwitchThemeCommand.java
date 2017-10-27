package seedu.address.logic.commands;

import static seedu.address.model.ThemeUnit.THEME_DARK_UNIT;
import static seedu.address.model.ThemeUnit.THEME_LIGHT_UNIT;
import static seedu.address.model.ThemeUnit.getCurrentThemeUnit;
import static seedu.address.model.ThemeUnit.setCurrentThemeUnit;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchThemeRequestEvent;

/**
 * Toggle between light and dark theme.
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "swt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle between light and dark theme.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SWITCH_THEME_MESSAGE = "Switched theme.";

    private boolean isLight = true;

    @Override
    public CommandResult execute() {
        toggle();
        EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
        return new CommandResult(SHOWING_SWITCH_THEME_MESSAGE);
    }

    /**
     * Toggle between light and dark theme
     */
    private void toggle() {
        switch (getCurrentThemeUnit()) {
        case THEME_LIGHT_UNIT:
            isLight = false;
            setCurrentThemeUnit(THEME_DARK_UNIT);
            break;

        case THEME_DARK_UNIT:
            isLight = true;
            setCurrentThemeUnit(THEME_LIGHT_UNIT);
            break;
        }

    }
}
