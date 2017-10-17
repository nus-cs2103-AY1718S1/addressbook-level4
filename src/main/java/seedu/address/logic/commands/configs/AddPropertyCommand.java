package seedu.address.logic.commands.configs;

import static seedu.address.logic.commands.configs.ConfigCommand.ConfigType.ADD_PROPERTY;

import java.util.regex.PatternSyntaxException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

/**
 * Adds a new property to the application.
 */
public class AddPropertyCommand extends ConfigCommand {
    private static final String MESSAGE_DUPLICATE_PROPERTY =
            "Another property with the same short name already exists in the application.";
    private static final String MESSAGE_INVALID_REGEX = "The regular expression you provide is invalid.";

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
        try {
            model.addProperty(shortName, fullName, constraintMessage, regex);
            return new CommandResult(String.format(MESSAGE_SUCCESS, configValue));
        } catch (DuplicatePropertyException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        } catch (PatternSyntaxException pse) {
            throw new CommandException(MESSAGE_INVALID_REGEX);
        }
    }
}
