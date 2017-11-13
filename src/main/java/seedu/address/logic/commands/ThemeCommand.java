//@@author Jemereny
package seedu.address.logic.commands;

import seedu.address.ui.UiStyle;

/**
 * change the theme of the address book
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_SHORT = "t";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";
    public static final String LIGHT_THEME2 = "holy";
    public static final String DARK_THEME2 = "evil";

    public static final String MESSAGE_SUCCESS = "Theme has been changed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes to selected theme. "
            + "Parameters: light/dark";

    private String theme;

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        if (theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME) || theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME2)) {
            UiStyle.getInstance().setToLightTheme();
        } else {
            UiStyle.getInstance().setToDarkTheme();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && theme.equals(((ThemeCommand) other).theme));
    }

    @Override
    public String toString() {
        return theme;
    }
}
