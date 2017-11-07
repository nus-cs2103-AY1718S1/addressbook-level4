package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.parser.AutoCorrectCommand;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + SHOWING_HELP_MESSAGE);
        }
    }
}
