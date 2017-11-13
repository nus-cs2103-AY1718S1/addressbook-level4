//@@author jshoung
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.ShowSummaryRequestEvent;

/**
 * Opens ContactHub command summary
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows ContactHub command summary.\n"
            + "Example: " + COMMAND_WORD;

    static final String SHOWING_SUMMARY_MESSAGE = "Opened command summary.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        EventsCenter.getInstance().post(new ShowSummaryRequestEvent());
        return new CommandResult(SHOWING_SUMMARY_MESSAGE);
    }
}
