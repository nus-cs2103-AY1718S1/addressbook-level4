package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.LIST_OF_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.hints.AddCommandHint;
import seedu.address.logic.commands.hints.DeleteCommandHint;
import seedu.address.logic.commands.hints.EditCommandHint;
import seedu.address.logic.commands.hints.FindCommandHint;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Class that is responsible for generating hints based on user input
 * Contains one public method generateHint which returns an appropriate hint based on input
 */
public class HintParser {

    private static String commandWord;
    private static String arguments;
    private static String userInput;

    //@@author goweiwen
    private static final ArrayList<String> COMMAND_LIST = new ArrayList<>(Arrays.asList(
        "add", "alias", "clear", "delete", "edit", "exit", "find", "help", "history", "list",
        "music", "redo", "remark", "select", "unalias", "undo"
    ));

    /**
     * Parses {@code String input} and returns an appropriate autocompletion
     */
    public static String autocomplete(String input) {
        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return "";
        }

        userInput = input;
        commandWord = command[0];
        arguments = command[1];

        String hint;

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            AddCommandHint addCommandHint = new AddCommandHint(userInput, arguments);
            addCommandHint.parse();
            return addCommandHint.autocomplete();

        case EditCommand.COMMAND_WORD:
            EditCommandHint editCommandHint = new EditCommandHint(userInput, arguments);
            editCommandHint.parse();
            return editCommandHint.autocomplete();

        case FindCommand.COMMAND_WORD:
            FindCommandHint findCommandHint = new FindCommandHint(userInput, arguments);
            findCommandHint.parse();
            return findCommandHint.autocomplete();

        case DeleteCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
            DeleteCommandHint deleteCommandHint = new DeleteCommandHint(userInput, arguments);
            deleteCommandHint.parse();
            return deleteCommandHint.autocomplete();

        case MusicCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return commandWord + " " + (MusicCommand.isPlaying() ? "pause" : "play");
            }
            hint = autocompleteFromList(arguments.trim(), new String[] {"play", "pause", "stop"});
            return commandWord + (hint != null ? " " + hint : arguments);

        default:
            hint = autocompleteCommand(commandWord);
            return hint != null ? hint + " " : input;
        }
    }

    /**
     * Parses {@code String command} and returns the closest matching command word, or null if nothing
     * matches.
     */
    public static String autocompleteCommand(String command) {
        List<String> commands = new ArrayList<>();
        // We add from COMMAND_LIST first because we want to autocomplete them first.
        commands.addAll(COMMAND_LIST);
        commands.addAll(UserPrefs.getInstance().getAliases().getAllAliases());
        String[] list = commands.toArray(new String[commands.size()]);
        return autocompleteFromList(command, list);
    }

    /**
     * Parses {@code String input} and returns the closest matching string in {@code String[] strings},
     * or null if nothing matches.
     */
    public static String autocompleteFromList(String input, String[] strings) {
        for (String string : strings) {
            if (string.startsWith(input)) {
                return string;
            }
        }
        return null;
    }

    //@@author nicholaschuayunzhi
    /**
     * Parses {@code String input} and returns an appropriate hint
     */
    public static String generateHint(String input) {
        //the ordering matters as prefix hints are generated inorder
        assert LIST_OF_PREFIXES.equals(Arrays.asList(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_REMARK, PREFIX_AVATAR, PREFIX_EMPTY));

        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return " type help for guide";
        }

        userInput = input;
        commandWord = command[0];
        arguments = command[1];
        String hintContent = generateHintContent();

        return hintContent;
    }

    /**
     * returns an appropriate hint based on commandWord and arguments
     * userInput and arguments are referenced to decide whether whitespace should be added to
     * the front of the hint
     */
    private static String generateHintContent() {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return generateAddHint();
        case AliasCommand.COMMAND_WORD:
            return " creates an alias";
        case EditCommand.COMMAND_WORD:
            return generateEditHint();
        case FindCommand.COMMAND_WORD:
            return generateFindHint();
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return generateDeleteAndSelectHint();
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
     * returns a parameter based on {@code prefix}
     */
    private static String prefixIntoParameter(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "NAME";
        case PREFIX_PHONE_STRING:
            return "PHONE";
        case PREFIX_ADDRESS_STRING:
            return "ADDRESS";
        case PREFIX_EMAIL_STRING:
            return "EMAIL";
        case PREFIX_TAG_STRING:
            return "TAG";
        case PREFIX_REMARK_STRING:
            return "REMARK";
        case PREFIX_EMPTY_STRING:
            return "KEYWORD";
        default:
            return "KEYWORD";
        }
    }

    /**
     * returns a hint specific to the add command
     */
    private static String generateAddHint() {
        AddCommandHint addCommandHint = new AddCommandHint(userInput, arguments);
        addCommandHint.parse();
        return addCommandHint.getArgumentHint() + addCommandHint.getDescription();

    }

    /**
     * returns a hint specific to the edit command
     */
    private static String generateEditHint() {
        EditCommandHint editCommandHint = new EditCommandHint(userInput, arguments);
        editCommandHint.parse();
        return editCommandHint.getArgumentHint() + editCommandHint.getDescription();
    }

    /**
     * returns a hint specific to the find command
     */
    private static String generateFindHint() {
        FindCommandHint findCommandHint = new FindCommandHint(userInput, arguments);
        findCommandHint.parse();
        return findCommandHint.getArgumentHint() + findCommandHint.getDescription();
    }

    /**
     * returns a hint specific to the select and delete command
     */
    private static String generateDeleteAndSelectHint() {

        DeleteCommandHint deleteCommandHint = new DeleteCommandHint(userInput, arguments);
        deleteCommandHint.parse();
        return deleteCommandHint.getArgumentHint() + deleteCommandHint.getDescription();

    }

}
