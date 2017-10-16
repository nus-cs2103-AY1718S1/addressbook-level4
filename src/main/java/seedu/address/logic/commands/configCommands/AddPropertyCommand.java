package seedu.address.logic.commands.configCommands;

import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.ADD_PROPERTY;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a new property to the application.
 */
public class AddPropertyCommand extends ConfigCommand {

    public AddPropertyCommand(String configValue) {
        super(ADD_PROPERTY, configValue);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
