package seedu.address.logic.commands;

import static seedu.address.model.ThemeUnit.THEME_DARK_UNIT;
import static seedu.address.model.ThemeUnit.THEME_LIGHT_UNIT;
import static seedu.address.model.ThemeUnit.getCurrentThemeUnit;
import static seedu.address.model.ThemeUnit.setCurrentThemeUnit;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchThemeRequestEvent;

//@@author cctdaniel
/**
 * Switch between light and dark theme
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch to light/dark theme. "
            + "Parameters: light/dark\n"
            + "Example: " + COMMAND_WORD + " " + LIGHT_THEME;

    public static final String SWITCH_THEME_SUCCESS_MESSAGE = "Switched theme to ";
    public static final String SWITCH_THEME_FAILURE_MESSAGE = "Please choose a different theme from the current one. ";

    public static final String LIGHT_THEME_CSS_FILE_NAME = "view/LightTheme.css";
    public static final String LIGHT_THEME_EXTENSIONS_CSS_FILE_NAME = "view/LightExtensions.css";
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String DARK_THEME_EXTENSIONS_CSS_FILE_NAME = "view/DarkExtensions.css";


    private boolean isLight = true;

    public ThemeCommand(String theme) {
        if (theme.equalsIgnoreCase(LIGHT_THEME)) {
            isLight = true;
        } else {
            isLight = false;
        }
    }

    @Override
    public CommandResult execute() {
        switch (getCurrentThemeUnit()) {
        case THEME_LIGHT_UNIT:
            if (isLight) {
                return new CommandResult(SWITCH_THEME_FAILURE_MESSAGE);
            } else {
                setCurrentThemeUnit(THEME_DARK_UNIT);
                EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
                return new CommandResult(SWITCH_THEME_SUCCESS_MESSAGE + DARK_THEME + ".");
            }
        case THEME_DARK_UNIT:
            if (!isLight) {
                return new CommandResult(SWITCH_THEME_FAILURE_MESSAGE);
            } else {
                setCurrentThemeUnit(THEME_LIGHT_UNIT);
                EventsCenter.getInstance().post(new SwitchThemeRequestEvent(isLight));
                return new CommandResult(SWITCH_THEME_SUCCESS_MESSAGE + LIGHT_THEME + ".");
            }
        default:
            break;
        }
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.isLight == (((ThemeCommand) other).isLight)); // state check
    }

    public boolean getIsLight() {
        return isLight;
    }
}
