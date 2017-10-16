package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.configCommands.AddPropertyCommand;
import seedu.address.logic.commands.configCommands.ChangeTagColorCommand;
import seedu.address.logic.commands.configCommands.ConfigCommand;
import seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType;
import seedu.address.logic.commands.configCommands.ImportCalenderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConfigCommand object
 */
public class ConfigCommandParser implements Parser<ConfigCommand> {
    /**
     * Used for initial separation of configuration change type and new configuration value.
     */
    private static final Pattern CONFIG_COMMAND_FORMAT = Pattern.compile("--(?<configType>\\S+)(?<configValue>.+)");

    @Override
    public ConfigCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Defensive programming here to use trim again.
        final Matcher matcher = CONFIG_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String configType = matcher.group("configType").trim();
        if (!checkConfigType(configType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final ConfigType enumConfigType = toEnumType(configType);
        final String configValue = matcher.group("configValue").trim();

        return checkConfigValue(enumConfigType, configValue);
    }

    private boolean checkConfigType(String type) {
        return ConfigCommand.toEnumConfigType.containsKey(type);
    }

    private ConfigType toEnumType(String type) {
        return ConfigCommand.toEnumConfigType.get(type);
    }

    /**
     * Validates the input for different {@link ConfigType} and creates an {@link ConfigCommand} accordingly.
     */
    private ConfigCommand checkConfigValue(ConfigType enumConfigType, String value) throws ParseException {
        switch (enumConfigType) {
        case ADD_PROPERTY:
            return checkAddProperty(value);
        case IMPORT_CALENDAR:
            return checkImportCalendar(value);
        case TAG_COLOR:
            return checkTagColor(value);
        default:
            System.err.println("Unknown ConfigType. Should never come to here.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    private ChangeTagColorCommand checkTagColor(String value) {
        return new ChangeTagColorCommand(value);
    }

    private AddPropertyCommand checkAddProperty(String value) {
        return new AddPropertyCommand(value);
    }

    private ImportCalenderCommand checkImportCalendar(String value) {
        return new ImportCalenderCommand(value);
    }
}
