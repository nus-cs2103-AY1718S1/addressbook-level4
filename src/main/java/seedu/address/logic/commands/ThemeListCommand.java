package seedu.address.logic.commands;

//@@author itsdickson

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowThemeRequestEvent;

/**
 * Lists all existing themes in the address book to the user.
 */
public class ThemeListCommand extends Command {

    public static final String COMMAND_WORD = "themes";

    public static final String MESSAGE_SUCCESS = "Listed all themes";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowThemeRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
