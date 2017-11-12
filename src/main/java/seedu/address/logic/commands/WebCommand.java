package seedu.address.logic.commands;

import static seedu.address.logic.parser.WebCommandParser.WEBSITES_MAP;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author hansiang93
/**
 * Lists all persons in the address book to the user.
 */
public class WebCommand extends Command {

    public static final String COMMAND_WORD = "web";
    public static final String COMMAND_ALIAS = "w";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the social links of the selected person in the web view on the right.\n"
            + "Parameters: 'facebook' OR 'instagram' OR 'maps' OR 'search' OR 'linkedin' OR 'personal'\n"
            + "Example: " + COMMAND_WORD + " facebook";

    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD
            + " {[facebook|instagram|maps|search|personal]}";

    public static final String MESSAGE_SUCCESS = "WebLink loading...";

    private final String targetWebsite;

    public WebCommand(String targetWebsite) {
        this.targetWebsite = targetWebsite;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!WEBSITES_MAP.containsValue(targetWebsite)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEBLINK_TAG);
        }
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
