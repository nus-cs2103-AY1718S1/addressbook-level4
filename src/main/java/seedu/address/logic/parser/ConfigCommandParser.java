package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ConfigCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConfigCommand object
 */
public class ConfigCommandParser implements Parser<ConfigCommand> {
    /**
     * Used for initial separation of configuration change type and new configuration value.
     */
    private static final Pattern CONFIG_COMMAND_FORMAT = Pattern.compile("(--?<configType>\\S+)(?<configValue>.+)");

    @Override
    public ConfigCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // No need to use trim() here because it has been done in AddressBook Parser.
        final Matcher matcher = CONFIG_COMMAND_FORMAT.matcher(args);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String configType = matcher.group("configType").trim();
        final String configValue = matcher.group("configValue").trim();

        return new ConfigCommand(configType, configValue);
    }
}
