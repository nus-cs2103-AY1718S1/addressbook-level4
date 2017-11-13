package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;
//@@author Valerieyue
/**
 * Shows the birthday statistics.
 */
public class BirthdayStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "birthdaystatistics";
    public static final String COMMAND_ALIAS = "bstats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the birthday statistics.\n"
            + "Example: " + COMMAND_WORD;
    public static final String BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT = "BirthdayStatisticsPanel";

    public static final String MESSAGE_SUCCESS = "Birthday statistics displayed";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance()
                .post(new ChangeInformationPanelRequestEvent(BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

