package seedu.address.ui;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.Prefix;

import java.util.Arrays;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public enum AutocompleteCommand {
    ADD,
    CLEAR,
    DELETE,
    DELETE_TAG,
    EDIT,
    EXIT,
    FIND,
    HELP,
    HISTORY,
    IMPORT,
    LIST,
    NONE,
    REDO,
    SELECT,
    TAB,
    UNDO;

    public static final String[] allCommands = {"add", "clear", "delete", "deleteTag", "edit", "exit", "find", "help",
            "history", "import", "list", "redo", "select", "tab", "undo"};

    public static final Prefix[] allPrefixes = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
            PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    public static final Prefix[] requiredPrefixes = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
            PREFIX_DELIVERY_DATE };

    public static final Prefix[] optionalPrefixes = {PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    private static final String[] commandsWithIndexes = {"delete", "edit", "select"};

    private static final String[] commandsWithPrefixes = {"add", "edit"};

    public static AutocompleteCommand getInstance(String commandName) {

        switch (commandName) {
            case AddCommand.COMMAND_WORD:
                return ADD;

            case ClearCommand.COMMAND_WORD:
                return CLEAR;

            case DeleteCommand.COMMAND_WORD:
                return DELETE;

            case DeleteTagCommand.COMMAND_WORD:
                return DELETE_TAG;

            case EditCommand.COMMAND_WORD:
                return EDIT;

            case ExitCommand.COMMAND_WORD:
                return EXIT;

            case FindCommand.COMMAND_WORD:
                return FIND;

            case HelpCommand.COMMAND_WORD:
                return HELP;

            case HistoryCommand.COMMAND_WORD:
                return HISTORY;

            case ImportCommand.COMMAND_WORD:
                return IMPORT;

            case ListCommand.COMMAND_WORD:
                return LIST;

            case RedoCommand.COMMAND_WORD:
                return REDO;

            case SelectCommand.COMMAND_WORD:
                return SELECT;

            case TabCommand.COMMAND_WORD:
                return TAB;

            case UndoCommand.COMMAND_WORD:
                return UNDO;

            default:
                return NONE;
        }
    }

    public static boolean hasIndexParameter (String command) {
        return Arrays.asList(commandsWithIndexes).contains(command);
    }

    public static boolean hasPrefixParameter (String command) {
        return Arrays.asList(commandsWithPrefixes).contains(command);
    }


}
