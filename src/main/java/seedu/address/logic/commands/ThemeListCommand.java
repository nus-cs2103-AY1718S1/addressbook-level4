package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowThemeRequestEvent;

public class ThemeListCommand extends Command {

    public static final String COMMAND_WORD = "themeslist";
    public static final String COMMAND_ALIAS = "tl";

    public static final String MESSAGE_SUCCESS = "Listed all themes";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowThemeRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
