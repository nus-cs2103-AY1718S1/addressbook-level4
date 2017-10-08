package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *  Changes tag color mode in address book
 */
public class ToggleTagColorCommand extends Command {

    public static final String COMMAND_WORD = "tagcolor";
    public static final String MESSAGE_SUCCESS = "TagColor set to ";

    private final Logger logger = LogsCenter.getLogger(ToggleTagColorCommand.class);

    private boolean toSet;

    public ToggleTagColorCommand(boolean toSet) {
        this.toSet = toSet;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (toSet) {
            GuiSettings.onTagColored();
        } else
            GuiSettings.offTagColored();

        logger.fine("Tag color set to " + (toSet? "on": "off"));
        
        return new CommandResult(String.format("%s%s", MESSAGE_SUCCESS, toSet ? "on": "off"));
    }
}
