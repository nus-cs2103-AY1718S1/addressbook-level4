package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class WebCommand extends Command {

    public static final String COMMAND_WORD = "web";
    public static final String COMMAND_ALIAS = "w";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Social Site Loaded";

    private final String targetWebsite;

    public WebCommand(String targetWebsite) {
        this.targetWebsite = targetWebsite;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new WebsiteSelectionRequestEvent(targetWebsite));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
