package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

public class BirthdaysCommand extends Command{
    public static final String COMMAND_WORD = "birthdays";
    public static final String COMMAND_ALIAS = "bd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays dialog box with names of contacts "
            + "who have their birthdays today.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_BIRTHDAY_MESSAGE = "Opened birthday dialog box.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_BIRTHDAY_MESSAGE);
    }

}
