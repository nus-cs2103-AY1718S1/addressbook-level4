package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.feather.Feather;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MusicCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Aliases;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Map<String, Ikon> Icons = new HashMap<>();
    static {
        Icons.put(MusicCommand.COMMAND_WORD, Feather.FTH_PLAY);
        Icons.put(AddCommand.COMMAND_WORD, Feather.FTH_PLUS);
        Icons.put(AliasCommand.COMMAND_WORD, Feather.FTH_LINK);
        Icons.put(UnaliasCommand.COMMAND_WORD, Feather.FTH_CROSS);
        Icons.put(EditCommand.COMMAND_WORD, Feather.FTH_FILE_ADD);
        Icons.put(SelectCommand.COMMAND_WORD, Feather.FTH_HEAD);
        Icons.put(DeleteCommand.COMMAND_WORD, Feather.FTH_TRASH);
        Icons.put(ClearCommand.COMMAND_WORD, Feather.FTH_CROSS);
        Icons.put(FindCommand.COMMAND_WORD, Feather.FTH_SEARCH);
        Icons.put(ListCommand.COMMAND_WORD, Feather.FTH_PAPER);
        Icons.put(HistoryCommand.COMMAND_WORD, Feather.FTH_CLOCK);
        Icons.put(ExitCommand.COMMAND_WORD, Feather.FTH_POWER);
        Icons.put(HelpCommand.COMMAND_WORD, Feather.FTH_HELP);
        Icons.put(UndoCommand.COMMAND_WORD, Feather.FTH_ARROW_LEFT);
        Icons.put(RedoCommand.COMMAND_WORD, Feather.FTH_ARROW_RIGHT);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String command} and returns itself if it is a valid command.
     */
    public static String parseCommand(String command) {
        requireNonNull(command);
        switch (command) {
        case AddCommand.COMMAND_WORD:
        case AliasCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
            return command;
        default:
            return null;
        }
    }

    /**
     * Parses {@code String command} and returns the corresponding {@code Ikon icon}
     * if valid.
     */
    public static Ikon parseIconCode(String command) {
        requireNonNull(command);
        return Icons.get(command);
    }

    /**
     * Parses {@code String userInput} and returns an array of {commandWord, arguments} if valid.
     */
    public static String[] parseCommandAndArguments(String userInput) throws ParseException {
        requireNonNull(userInput);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        Aliases aliases = UserPrefs.getInstance().getAliases();
        String aliasedCommand = aliases.getCommand(commandWord);
        if (aliasedCommand != null) {
            commandWord = aliasedCommand;
        }

        return new String[] {commandWord, arguments};
    }

    /**
     * Parses {@code String commandWord, String arguments} and returns an appropriate hint
     */
    public static String generateHint(String userInput) {


        String[] command;
        try {
            command = parseCommandAndArguments(userInput);
        } catch (ParseException e) {
            return "";
        }

        String commandWord = command[0];
        String arguments = command[1];


        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return generateAddHint(arguments, userInput);
        case AliasCommand.COMMAND_WORD:
            return " creates an alias";
        case EditCommand.COMMAND_WORD:
            return generateEditHint(arguments, userInput);
        case FindCommand.COMMAND_WORD:
            return generatePrefixHint(arguments, userInput);
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return generateIndexHint(arguments, userInput);
        case ClearCommand.COMMAND_WORD:
            return " clears address book";
        case ListCommand.COMMAND_WORD:
            return " lists all people";
        case HistoryCommand.COMMAND_WORD:
            return " show command history";
        case ExitCommand.COMMAND_WORD:
            return " exits the app";
        case HelpCommand.COMMAND_WORD:
            return " shows user guide";
        case UndoCommand.COMMAND_WORD:
            return " undo command";
        case RedoCommand.COMMAND_WORD:
            return " redo command";
        default:
            return " type help for guide";
        }
    }

    /**
     * Parses {@code String arguments} and returns a formatted hint based on {@code hint}
     * hint is the actual meaning of the keyword after the prefix (ie PHONE in p/PHONE)
     */
    private static String endsWithPrefix(Prefix p, String arguments, String hint) {
        String prefixLetter = " " + (p.getPrefix().toCharArray()[0]); // " n"
        String identifier = "" + (p.getPrefix().toCharArray()[1]); // "/"

        if (arguments.endsWith(p.getPrefix())) {
            return hint;
        } else if (arguments.endsWith(prefixLetter) && !arguments.contains(p.getPrefix())) {
            return identifier + hint;
        } else {
            return null;
        }
    }

    /**
     * Parses {@code String commandWord, String arguments} and returns an appropriate hint
     */
    private static String generatePrefixHint(String arguments, String userInput) {
        String endHint;

        if ((endHint = endsWithPrefix(PREFIX_NAME, arguments, "NAME")) != null) {
            return endHint;
        }

        if ((endHint = endsWithPrefix(PREFIX_PHONE, arguments, "PHONE")) != null) {
            return endHint;
        }

        if ((endHint = endsWithPrefix(PREFIX_EMAIL, arguments, "EMAIL")) != null) {
            return endHint;
        }

        if ((endHint = endsWithPrefix(PREFIX_ADDRESS, arguments, "ADDRESS")) != null) {
            return endHint;
        }

        if ((endHint = endsWithPrefix(PREFIX_TAG, arguments, "TAG")) != null) {
            return endHint;
        }

        return (userInput.endsWith(" ")) ? "prefix/KEYWORD" : " prefix/KEYWORD";
    }

    /**
     * Parses {@code String commandWord, String arguments} and returns an appropriate hint
     */
    private static String generateAddHint(String arguments, String userInput) {

        String hint = generatePrefixHint(arguments, userInput);
        String whiteSpace = (userInput).endsWith(" ") ? "" : " ";

        if (!(hint.equals(" prefix/KEYWORD")) && !(hint.equals("prefix/KEYWORD"))) {
            return hint;
        }
        ArgumentMultimap argumentMultimap =
            ArgumentTokenizer
                .tokenize(arguments,
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // returns true if prefix is not present or its value is empty
        Function<Prefix, Boolean> isPrefixNotPresent =
            prefix -> !argumentMultimap.getValue(prefix).isPresent()
                    || argumentMultimap.getValue(prefix).get().equals("");

        if (isPrefixNotPresent.apply(PREFIX_NAME)) {
            return whiteSpace + "n/NAME";
        }

        if (isPrefixNotPresent.apply(PREFIX_PHONE)) {
            return whiteSpace +  "p/PHONE";
        }

        if (isPrefixNotPresent.apply(PREFIX_EMAIL)) {
            return whiteSpace + "e/EMAIL";
        }

        if (isPrefixNotPresent.apply(PREFIX_ADDRESS)) {
            return whiteSpace +  "a/ADDRESS";
        }

        if (isPrefixNotPresent.apply(PREFIX_TAG)) {
            return whiteSpace +  "t/TAG";
        }

        return "";

    }

    /**
     * Parses {@code String arguments, String userInput} and returns an index hint
     * if there is an index provided, return "" else return index
     */
    private static String generateIndexHint(String arguments, String userInput) {
        try {
            ParserUtil.parseIndex(arguments);
            return "";
        } catch (IllegalValueException ive) {
            if (arguments.matches(".*\\s\\d+\\s.*")) {
                return "";
            }
            String whiteSpace = (userInput).endsWith(" ") ? "" : " ";
            return whiteSpace + "index";
        }
    }

    /**
     * Parses {@code String arguments, String userInput} and returns an index hint
     * if there is an index provided, return "" else return index
     */
    private static String generateEditHint(String arguments, String userInput) {
        String indexHint = generateIndexHint(arguments, userInput);

        if (indexHint.equals("index") || indexHint.equals(" index")) {
            return indexHint;
        }

        return generatePrefixHint(arguments, userInput);

    }
}
