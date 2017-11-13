package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_FULL_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_REGEX;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_SHORT_NAME;
import static seedu.address.model.property.PropertyManager.DEFAULT_MESSAGE;
import static seedu.address.model.property.PropertyManager.DEFAULT_REGEX;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.configs.AddPropertyCommand;
import seedu.address.logic.commands.configs.ChangeTagColorCommand;
import seedu.address.logic.commands.configs.ConfigCommand;
import seedu.address.logic.commands.configs.ConfigCommand.ConfigType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.util.ParserUtil;

//@@author yunpengn
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
    // A valid RGB value should be 3-bit or 6-bit hexadecimal number.
    private static final Pattern RGB_FORMAT = Pattern.compile("#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})");
    // Only contains alphabets (a-z or A-Z)
    private static final Pattern ONLY_ALPHABET = Pattern.compile("[a-zA-Z]+");

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

        // Get the tag name and the customize new color for that tag.
        final String tagName = matcher.group("tagName").trim();
        String tagColor = matcher.group("tagNewColor").trim();

        // Checks whether the given color is valid.
        if (!isValidColorCode(tagColor)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, COLOR_CODE_WRONG));
        }

        return new ChangeTagColorCommand(value, tagName, tagColor);
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
     * Checks whether the given string is a valid RGB value or a fully-alphabetical string (we do not check whether it
     * is one of the 140 pre-defined CSS color names).
     *
     * TODO: Search for any API to check whether it is one of 140 pre-defined names.
     *
     * @see <a href=https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#typecolor>
     *     JavaFX CSS Reference Guide</a>
     */
    private boolean isValidColorCode(String color) {
        // Either all letters or a valid RGB value.
        return ONLY_ALPHABET.matcher(color).matches() || RGB_FORMAT.matcher(color).matches();

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
