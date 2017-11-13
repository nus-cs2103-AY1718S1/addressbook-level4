package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.ExportToFileRequestEvent;

// @@author johnweikangong
/**
 * Exports the addressbook to CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports your contacts into a CSV file.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully exported your contacts to Bevy.csv file.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExportToFileRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
