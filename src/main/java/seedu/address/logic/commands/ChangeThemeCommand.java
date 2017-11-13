package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
//@@author moomeowroar
/**
 * Change theme
 */

public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";
    public static final String COMMAND_HELP = "theme THEME_NAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme. \n"
            + COMMAND_WORD + " <dark/light/youtube>";


    public static final String MESSAGE_NO_THEME = "Invalid theme.";
    public static final String MESSAGE_CHANGE_SUCCESS = "Theme changed successfully.";

    private final String themeName;

    /**
     * @param String Name of theme to change
     */
    public ChangeThemeCommand(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeThemeEvent(themeName));
        return new CommandResult(MESSAGE_CHANGE_SUCCESS);
    }

}
