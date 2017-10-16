package seedu.address.logic.commands.configCommands;

import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.TAG_COLOR;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the color of an existing tag.
 */
public class ChangeTagColorCommand extends ConfigCommand {
    public ChangeTagColorCommand(String configValue) {
        super(TAG_COLOR, configValue);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
