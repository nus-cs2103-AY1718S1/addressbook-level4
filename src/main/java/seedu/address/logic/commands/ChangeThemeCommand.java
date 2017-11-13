package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ThemeChangeEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author JasmineSee
/**
 * Changes colour theme of application.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "th";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes colour theme. Current colour themes: Dark, White, Green\n"
            + "Parameters: Colour theme\n"
            + "Example: " + COMMAND_WORD + " white";

    public static final String MESSAGE_CHANGE_THEME_SUCCESS = "Theme changed";

    private final String theme;

    public ChangeThemeCommand(String theme) {

        this.theme = theme;

    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ThemeChangeEvent(theme));
        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && theme.equals(((ChangeThemeCommand) other).theme));
    }


}
