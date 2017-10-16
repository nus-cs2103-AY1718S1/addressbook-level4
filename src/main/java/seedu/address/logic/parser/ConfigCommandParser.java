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

    /* Regular expressions for validation. ArgumentMultiMap not applicable here. */
    private static final Pattern CONFIG_COMMAND_FORMAT = Pattern.compile("--(?<configType>\\S+)(?<configValue>.+)");
    private static final Pattern TAG_COLOR_FORMAT =
            Pattern.compile("(?<tagName>\\p{Alnum}+)\\s+(?<tagNewColor>#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$)");

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

    /**
     * Creates an {@link ChangeTagColorCommand}.
     */
    private ChangeTagColorCommand checkTagColor(String value) throws ParseException {
        final Matcher matcher = TAG_COLOR_FORMAT.matcher(value.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String tagName = matcher.group("tagName").trim();
        final String tagColor = matcher.group("tagNewColor").trim();

        return new ChangeTagColorCommand(value, tagName, tagColor);
    }

    /**
     * Creates an {@link AddPropertyCommand}.
     */
    private AddPropertyCommand checkAddProperty(String value) throws ParseException {
        return new AddPropertyCommand(value);
    }

    private ImportCalenderCommand checkImportCalendar(String value) {
        return new ImportCalenderCommand(value);
    }
}
