//@@author keloysiusmak
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangedThemeEvent;
import seedu.address.ui.MainWindow;

/**
 * Sets a theme for the TunedIn Application
 */
public class SetThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "settheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a theme for the .\n"
            + "Parameters: THEME ('summer', 'spring', 'autumn' or 'winter')\n"
            + "Example: " + COMMAND_WORD + " spring";

    public static final String MESSAGE_CHANGED_THEME_SUCCESS = "Changed Theme: %1$s\nYour changes has been saved and "
            + "will show up for all future accesses.";

    private final String theme;

    public SetThemeCommand() {
        this.theme = "summer";
    }

    public SetThemeCommand(String setTheme) {
        this.theme = setTheme;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        if (!((this.theme.equals("summer"))
                || (this.theme.equals("spring"))
                || (this.theme.equals("winter"))
                || (this.theme.equals("autumn")))) {
            return new CommandResult(String.format(Messages.MESSAGE_WRONG_THEME, this.theme));
        }
        config.setTheme(this.theme);
        try {
            String image = MainWindow.class.getResource("/images/" + config.getTheme() + ".jpg").toExternalForm();
            MainWindow mainWindow = ui.getMainWindow();

            mainWindow.getRoot().setStyle("-fx-background-image: url('" + image + "'); ");
        } catch (NullPointerException e) {
            ;
        }
        EventsCenter.getInstance().post(new ChangedThemeEvent(this.theme));
        return new CommandResult(String.format(MESSAGE_CHANGED_THEME_SUCCESS, this.theme));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetThemeCommand // instanceof handles nulls
                && this.theme.equals(((SetThemeCommand) other).theme)); // state check
    }
}
