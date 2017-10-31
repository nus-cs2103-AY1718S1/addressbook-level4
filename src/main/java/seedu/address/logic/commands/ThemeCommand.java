package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.UiTheme;

/**
 * Changes the application theme to the user specified option.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";
    public static final String COMMAND_OPTION_DAY = "day";
    public static final String COMMAND_OPTION_NIGHT = "night";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the application theme to the specified option.\n"
            + "Options: \n"
            + "\t" + COMMAND_OPTION_DAY + " - Changes the application theme to a light color scheme\n"
            + "\t" + COMMAND_OPTION_NIGHT + " - Changes the application theme to a dark color scheme\n"
            + "Example: \n"
            + "\t" + COMMAND_WORD + " -day\n"
            + "\t" + COMMAND_WORD + " -night\n";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme successfully applied";

    private final String optedTheme;

    public ThemeCommand (String args) {
        this.optedTheme = args;
    }

    @Override
    public CommandResult execute() throws CommandException {
        UiTheme.getInstance().changeTheme(optedTheme);
        return new CommandResult(MESSAGE_THEME_CHANGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.optedTheme.equals(((ThemeCommand) other).optedTheme)); // state check
    }
}
