package seedu.address.logic.commands.configCommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIG_TYPE;

import java.util.HashMap;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Customizes the configuration of the application.
 */
public abstract class ConfigCommand extends Command {
    public static final String COMMAND_WORD = "config";
    public static final String COMMAND_ALIAS = "cfg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the configuration of the application. "
            + "Parameters: "
            + PREFIX_CONFIG_TYPE + "CONFIG_TYPE "
            + "NEW_CONFIG_VALUE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONFIG_TYPE + "set-tag-color "
            + "friends #9381a0";

    public static final String MESSAGE_SUCCESS = "Configuration changed: %1$s";

    public enum ConfigType {
        ADD_PROPERTY, IMPORT_CALENDAR, TAG_COLOR,
    }

    public static final HashMap<String, ConfigType> toEnumConfigType = new HashMap<>();

    static {
        toEnumConfigType.put("add-property", ConfigType.ADD_PROPERTY);
        toEnumConfigType.put("set-tag-color", ConfigType.TAG_COLOR);
        toEnumConfigType.put("import-calendar", ConfigType.IMPORT_CALENDAR);
    }

    private ConfigType configType;
    private String configValue;

    public ConfigCommand(ConfigType configType, String configValue) {
        this.configType = configType;
        this.configValue = configValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConfigCommand // instanceof handles nulls
                && configType.equals(((ConfigCommand) other).configType)
                && configValue.equals(((ConfigCommand) other).configValue));
    }
}
