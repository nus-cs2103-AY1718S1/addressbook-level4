package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.commands.alias.UnaliasCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.HideCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.ListPinCommand;
import seedu.address.logic.commands.person.PinCommand;
import seedu.address.logic.commands.person.RemarkCommand;
import seedu.address.logic.commands.person.SelectCommand;
import seedu.address.logic.commands.person.SortCommand;
import seedu.address.logic.commands.person.UnpinCommand;
import seedu.address.logic.parser.alias.AliasCommandParser;
import seedu.address.logic.parser.alias.UnaliasCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.logic.parser.person.DeleteCommandParser;
import seedu.address.logic.parser.person.EditCommandParser;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.logic.parser.person.HideCommandParser;
import seedu.address.logic.parser.person.PinCommandParser;
import seedu.address.logic.parser.person.RemarkCommandParser;
import seedu.address.logic.parser.person.SelectCommandParser;
import seedu.address.logic.parser.person.SortCommandParser;
import seedu.address.logic.parser.person.UnpinCommandParser;
import seedu.address.model.alias.ReadOnlyAliasToken;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern KEYWORD_PATTERN =
            Pattern.compile("([^\\s/]+)([\\s/]+|$)");

    private final Map<String, Parser<? extends Command>> commandMap;
    private final Map<String, ReadOnlyAliasToken> aliasMap;
    private final ObservableList<ReadOnlyAliasToken> aliasList = FXCollections.observableArrayList();

    public AddressBookParser() {
        this.commandMap = new HashMap<String, Parser<? extends Command>>();
        this.aliasMap = new HashMap<String, ReadOnlyAliasToken>();
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        final String checkedCommandWord = commandWordCheck(commandWord);
        final String checkedArguments = argumentsCheck(checkedCommandWord, arguments);


        switch (checkedCommandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(checkedArguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(checkedArguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(checkedArguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(checkedArguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case PinCommand.COMMAND_WORD:
            return new PinCommandParser().parse(arguments);

        case ListPinCommand.COMMAND_WORD:
            return new ListPinCommand();

        case UnpinCommand.COMMAND_WORD:
            return new UnpinCommandParser().parse(arguments);

        case HideCommand.COMMAND_WORD:
            return new HideCommandParser().parse(checkedArguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(checkedArguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(checkedArguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(checkedArguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(checkedArguments);

        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandParser().parse(checkedArguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if the commandWord is used with an alias
     *
     * @param commandWord - the first arg of the command statement
     * @return checkedCommandWord , containing the representation if it has been aliased
     */
    private String commandWordCheck(String commandWord) {
        String checkedCommandWord = commandWord;

        ReadOnlyAliasToken token = aliasMap.get(commandWord);
        if (token != null) {
            checkedCommandWord = token.getRepresentation().representation;
        }
        return checkedCommandWord;
    }

    /**
     * Checks if any of the arguments have an alias
     *
     * @param arguments - the arguments after the command word
     * @returns a string that contains the arguments with replaced representations if any of them had aliases.
     */
    private String argumentsCheck(String checkedCommandWord, String arguments) {

        if ("alias".equals(checkedCommandWord) || ("unalias".equals(checkedCommandWord))) {
            return arguments;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        Matcher matcher = KEYWORD_PATTERN.matcher(arguments);

        while (matcher.find()) {
            String keyword = matcher.group(1);
            String spaces = matcher.group(2); // The amount of spaces entered is kept the same
            ReadOnlyAliasToken token = aliasMap.get(keyword);
            if (token != null) {
                keyword = token.getRepresentation().representation;
            }
            builder.append(keyword);
            builder.append(spaces);
        }

        return builder.toString();
    }

    /**
     * Adds an AliasToken to be used by parser to replace all alias's keyword with its representation
     * before parsing.
     *
     * @param toAdd
     * @return
     */
    public boolean addAliasToken(ReadOnlyAliasToken toAdd) {
        requireNonNull(toAdd);

        if (aliasMap.containsKey(toAdd.getKeyword().keyword)) {
            return false;
        }

        if (isCommandRegistered(toAdd.getKeyword().keyword)) {
            return false;
        }

        aliasList.add(toAdd);
        aliasMap.put(toAdd.getKeyword().keyword, toAdd);
        return true;
    }

    /**
     * Removes an AliasToken that is used by parser to replace all alias's keyword with its representation
     * before parsing.
     *
     * @param toRemove
     * @return
     */
    public boolean removeAliasToken(ReadOnlyAliasToken toRemove) {
        requireNonNull(toRemove);

        ReadOnlyAliasToken token = aliasMap.remove(toRemove.getKeyword().keyword);
        if (token != null) {
            return aliasList.remove(token);
        } else {
            return false;
        }
    }


    /**
     * Registers a command parser into the commandMap
     *
     * @param commandParser the map of command parsers
     * @return true if successfully registered, false if parser with same command word
     * already registered or if an alias with the same keyword is previously added.
     */
    public boolean registerCommandParser(Parser<? extends Command> commandParser) {
        requireNonNull(commandParser);

        if (commandMap.containsKey(commandParser.getCommandWord())) {
            return false;
        }
        if (aliasMap.containsKey(commandParser.getCommandWord())) {
            return false;
        }

        commandMap.put(commandParser.getCommandWord(), commandParser);
        return true;
    }

    /**
     * Registers the command words of commands that do not have a parser into the commandMap
     */
    public void registerOtherCommands() {
        commandMap.put("clear", null);
        commandMap.put("exit", null);
        commandMap.put("help", null);
        commandMap.put("history", null);
        commandMap.put("list", null);
        commandMap.put("listpin", null);
        commandMap.put("redo", null);
        commandMap.put("undo", null);
    }

    public boolean isCommandRegistered(String header) {
        return commandMap.containsKey(header);
    }

    public Parser<? extends Command> unregisterCommandParser(String header) {
        return commandMap.remove(header);
    }

    public ObservableList<ReadOnlyAliasToken> getAliasTokenList() {
        return aliasList;
    }

}
