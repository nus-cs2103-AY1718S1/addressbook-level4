package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHORT_NAME;
import static seedu.address.model.property.PropertyManager.DEFAULT_MESSAGE;
import static seedu.address.model.property.PropertyManager.DEFAULT_REGEX;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.configs.AddPropertyCommand;
import seedu.address.logic.commands.configs.ChangeTagColorCommand;
import seedu.address.logic.commands.configs.ConfigCommand;
import seedu.address.logic.commands.configs.ConfigCommand.ConfigType;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConfigCommand object
 */
public class ConfigCommandParser implements Parser<ConfigCommand> {
    // Some messages ready to use.
    public static final String CONFIG_TYPE_NOT_FOUND = "The configuration you want to change is not "
            + "available or the command entered is incomplete.";
    public static final String COLOR_CODE_WRONG = "The color must be one of the pre-defined color names or "
            + "a valid hexadecimal RGB value";
    private static final String MESSAGE_REGEX_TOGETHER = "Constraint message and regular expression must be "
            + "both present or absent";

    /* Regular expressions for validation. ArgumentMultiMap not applicable here. */
    private static final Pattern CONFIG_COMMAND_FORMAT = Pattern.compile("--(?<configType>\\S+)(?<configValue>.+)");
    private static final Pattern TAG_COLOR_FORMAT = Pattern.compile("(?<tagName>\\p{Alnum}+)\\s+(?<tagNewColor>.+)");
    private static final Pattern RGB_FORMAT = Pattern.compile("#(?<rgbValue>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$)");

    // Some pre-defined colors for convenience.
    private static final HashMap<String, String> preDefinedColors = new HashMap<>();

    /**
     * Loads all pre-defined colors here. If you want to define more, you can get more color codes can be obtained from
     * https://www.w3schools.com/colors/colors_names.asp Make sure you put them in alphabetical order.
     */
    static {
        preDefinedColors.put("black", "#000000");
        preDefinedColors.put("blue", "#0000FF");
        preDefinedColors.put("brown", "#A52A2A");
        preDefinedColors.put("green", "#008000");
        preDefinedColors.put("red", "#FF0000");
        preDefinedColors.put("white", "#FFFFFF");
        preDefinedColors.put("yellow", "#FFFF00");
    }

    @Override
    public ConfigCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Defensive programming here to use trim again.
        final Matcher matcher = CONFIG_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
        }

        final String configType = matcher.group("configType").trim();
        if (!checkConfigType(configType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
        }

        final ConfigType enumConfigType = toEnumType(configType);
        final String configValue = matcher.group("configValue").trim();

        return checkConfigValue(enumConfigType, configValue);
    }

    private boolean checkConfigType(String type) {
        return ConfigCommand.TO_ENUM_CONFIG_TYPE.containsKey(type);
    }

    private ConfigType toEnumType(String type) {
        return ConfigCommand.TO_ENUM_CONFIG_TYPE.get(type);
    }

    /**
     * Validates the input for different {@link ConfigType} and creates an {@link ConfigCommand} accordingly.
     */
    private ConfigCommand checkConfigValue(ConfigType enumConfigType, String value) throws ParseException {
        switch (enumConfigType) {
        case ADD_PROPERTY:
            return checkAddProperty(value);
        case TAG_COLOR:
            return checkTagColor(value);
        default:
            System.err.println("Unknown ConfigType. Should never come to here.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
        }
    }

    /**
     * Creates an {@link ChangeTagColorCommand}.
     */
    private ChangeTagColorCommand checkTagColor(String value) throws ParseException {
        Matcher matcher = TAG_COLOR_FORMAT.matcher(value.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTagColorCommand.MESSAGE_USAGE));
        }

        final String tagName = matcher.group("tagName").trim();
        String tagColor = matcher.group("tagNewColor").trim();

        // Use the corresponding RGB value to replace pre-defined color names.
        if (preDefinedColors.containsKey(tagColor)) {
            tagColor = preDefinedColors.get(tagColor);
        }

        matcher = RGB_FORMAT.matcher(tagColor.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, COLOR_CODE_WRONG));
        }
        String colorRgbValue = matcher.group("rgbValue").trim();

        return new ChangeTagColorCommand(value, tagName, colorRgbValue);
    }

    /**
     * Creates an {@link AddPropertyCommand}.
     */
    private AddPropertyCommand checkAddProperty(String value) throws ParseException {
        /*
        * Hack here: ArgumentTokenizer requires a whitespace before each prefix to count for an occurrence. Thus, we
        * have to explicitly add a whitespace before the string so as to successfully extract the first prefix.
        */
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + value,
                PREFIX_SHORT_NAME, PREFIX_FULL_NAME, PREFIX_MESSAGE, PREFIX_REGEX);

        // shortName and fullName must be supplied by the user.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_SHORT_NAME, PREFIX_FULL_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPropertyCommand.MESSAGE_USAGE));
        }
        String shortName = argMultimap.getValue(PREFIX_SHORT_NAME).get();
        String fullName = capitalize(argMultimap.getValue(PREFIX_FULL_NAME).get());

        // message and regex must be supplied together or both be absent.
        if (!(ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MESSAGE, PREFIX_REGEX)
                || ParserUtil.arePrefixesAbsent(argMultimap, PREFIX_MESSAGE, PREFIX_REGEX))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REGEX_TOGETHER));
        }
        String message = argMultimap.getValue(PREFIX_MESSAGE).orElse(String.format(DEFAULT_MESSAGE, fullName));
        String regex = argMultimap.getValue(PREFIX_REGEX).orElse(DEFAULT_REGEX);

        return new AddPropertyCommand(value, shortName, fullName, message, regex);
    }

    /**
     * Converts the first letter in {@code str} to upper-case (only if it starts with an alphabet).
     */
    private String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }

        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
