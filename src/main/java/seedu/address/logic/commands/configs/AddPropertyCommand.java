package seedu.address.logic.commands.configs;

import static seedu.address.logic.commands.configs.ConfigCommand.ConfigType.ADD_PROPERTY;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a new property to the application.
 */
public class AddPropertyCommand extends ConfigCommand {
    private final String shortName;
    private final String fullName;
    private final String constraintMessage;
    private final String regex;

    public AddPropertyCommand(String configValue, String shortName, String fullName, String message, String regex) {
        super(ADD_PROPERTY, configValue);
        this.shortName = shortName;
        this.fullName = fullName;
        this.constraintMessage = message;
        this.regex = regex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
