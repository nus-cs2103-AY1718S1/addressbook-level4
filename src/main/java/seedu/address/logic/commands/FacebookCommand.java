package seedu.address.logic.commands;

//@@author LeeYingZheng
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowFacebookRequestEvent;

/**
 * Displays Facebook Log In page.
 */
public class FacebookCommand extends Command {



    public static final String COMMAND_WORDVAR_1 = "facebook";
    public static final String COMMAND_WORDVAR_2 = "fb";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + ": Shows facebook log in page.\n"
            + "Example: "
            + COMMAND_WORDVAR_1;

    public static final String SHOWING_FACEBOOK_MESSAGE = "Opened facebook window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowFacebookRequestEvent());
        return new CommandResult(SHOWING_FACEBOOK_MESSAGE);
    }
}
