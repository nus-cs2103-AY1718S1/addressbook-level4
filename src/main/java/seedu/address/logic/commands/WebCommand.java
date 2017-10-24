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
            + ": Displays the social links of the selected person in the web view on the right.\n"
            + "Parameters: 'facebook' OR 'insta' OR 'maps' OR 'search' OR 'linkedin' OR 'personal'\n"
            + "Example: " + COMMAND_WORD + " facebook";

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WebCommand // instanceof handles nulls
                && targetWebsite.equals(((WebCommand) other).targetWebsite));
    }
}
