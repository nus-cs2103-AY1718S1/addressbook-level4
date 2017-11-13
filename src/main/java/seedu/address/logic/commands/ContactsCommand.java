package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowContactsEvent;

//@@author huyuanrong
/**
 * Format useful contact instructions for every command for display.
 */
public class ContactsCommand extends Command {

    public static final String COMMAND_WORD = "emergency";
    public static final String COMMAND_WORD_ALIAS = "sos";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows useful contacts.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CONTACT_MESSAGE = "Opened contacts window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowContactsEvent());
        return new CommandResult(SHOWING_CONTACT_MESSAGE);
    }
}
