package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;
//@@author Valerieyue
/**
 * Shows the tag statistics.
 */
public class TagStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "tagstatistics";
    public static final String COMMAND_ALIAS = "tstats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the tag statistics.\n"
            + "Example: " + COMMAND_WORD;
    public static final String TAG_STATISTICS_PANEL_REQUEST_EVENT = "TagStatisticsPanel";

    public static final String MESSAGE_SUCCESS = "Tag statistics displayed";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance()
                .post(new ChangeInformationPanelRequestEvent(TAG_STATISTICS_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


