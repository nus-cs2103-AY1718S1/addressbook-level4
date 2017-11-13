package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
//@@author kosyoz
/**
 * Change the theme of address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the App. "
            + "Parameters: [Name of Stylesheet]";
    public static final String MESSAGE_SUCCESS = "Theme Changed to ";

    private final String theme;

    public ChangeThemeCommand(String theme) {
        this.theme = theme;
    }
    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));
        if (theme.equals("")) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS + theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.theme.equals(((ChangeThemeCommand) other).theme));
    }

}
