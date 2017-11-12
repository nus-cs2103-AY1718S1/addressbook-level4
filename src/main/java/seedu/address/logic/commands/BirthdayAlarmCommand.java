//@@author chilipadiboy
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBirthdayAlarmRequestEvent;

/**
 * Opens up the Reminders Panel
 */
public class BirthdayAlarmCommand extends Command {

    public static final String COMMAND_WORD = "reminders";
    public static final String COMMAND_SHORT = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the reminders window.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_REMINDERS_MESSAGE = "Opened reminders window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowBirthdayAlarmRequestEvent());
        return new CommandResult(SHOWING_REMINDERS_MESSAGE);
    }
}
