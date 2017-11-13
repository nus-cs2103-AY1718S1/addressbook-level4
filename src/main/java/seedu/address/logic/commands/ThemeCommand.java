package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.parser.Theme;
import seedu.address.logic.parser.ThemeList;

//@@author chuaweiwen
/**
 * Changes the theme of the address book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the address book\n"
            + "Parameter: THEME\n"
            + "List of available themes: "
            + ThemeList.THEME_DAY + ", "
            + ThemeList.THEME_NIGHT + "\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SET_THEME_SUCCESS = "Successfully set theme: %1$s";

    private final Theme theme;

    public ThemeCommand(Theme theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(theme));
        return new CommandResult(String.format(MESSAGE_SET_THEME_SUCCESS, theme.getTheme()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand e = (ThemeCommand) other;
        return theme.equals(e.theme);
    }
}
//@@author
