package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;

/**
 * Change the theme of address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the App. "
            + "Parameters: ";
    public static final String MESSAGE_SUCCESS = "Theme Changed to ";

    private final String theme;

    public ChangeThemeCommand( String theme)
    {
        this.theme = theme;
    }
    @Override
    public CommandResult execute()
    {
        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));
        return new CommandResult(String.format(MESSAGE_SUCCESS + theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.theme.equals(((ChangeThemeCommand) other).theme));
    }

}
