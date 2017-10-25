package seedu.address.logic.commands.configs;

import java.util.HashMap;

import seedu.address.logic.commands.Command;

/**
 * Customizes the configuration of the application.
 */
public abstract class ConfigCommand extends Command {
    public static final String COMMAND_WORD = "config";
    public static final String COMMAND_ALIAS = "cfg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the configuration of the application. "
            + "Parameters: " + "--CONFIG_TYPE "
            + "NEW_CONFIG_VALUE\n"
            + "Example: " + COMMAND_WORD + " --set-tag-color "
            + "friends #9381a0";

    public static final String MESSAGE_SUCCESS = "Configuration changed: %1$s";

    /**
     * Different types of sub-commands within {@link ConfigCommand}.
     */
    public enum ConfigType {
        ADD_PROPERTY, IMPORT_CALENDAR, TAG_COLOR
    }

    public static final HashMap<String, ConfigType> TO_ENUM_CONFIG_TYPE = new HashMap<>();

    static {
        TO_ENUM_CONFIG_TYPE.put("add-property", ConfigType.ADD_PROPERTY);
        TO_ENUM_CONFIG_TYPE.put("set-tag-color", ConfigType.TAG_COLOR);
        TO_ENUM_CONFIG_TYPE.put("import-calendar", ConfigType.IMPORT_CALENDAR);
    }

    protected String configValue;

    private ConfigType configType;

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
