package seedu.address.ui.autocompleter;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKING_NUMBER;

import java.util.Arrays;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TabCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.Prefix;

//@@author Kowalski985
/**
 * Represents the current command that the autocompleter recognises in the {@code CommandBox}
 */
public enum AutocompleteCommand {
    ADD,
    CLEAR,
    DELETE,
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

    public static final String[] ALL_COMMANDS = {"add", "clear", "delete", "edit", "exit", "find", "help",
        "history", "import", "list", "redo", "select", "tab", "undo"};

    public static final Prefix[] ALL_PREFIXES = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
        PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    private static final String[] commandsWithIndexes = {"delete", "edit", "select"};

    private static final String[] commandsWithPrefixes = {"add", "edit"};

    public static AutocompleteCommand getInstance(String commandName) {
        requireNonNull(commandName);
        switch (commandName) {
        case AddCommand.COMMAND_WORD:
            return ADD;

        case ClearCommand.COMMAND_WORD:
            return CLEAR;

        case DeleteCommand.COMMAND_WORD:
            return DELETE;

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

    /**
     * Returns true if {@code command} takes in an {@code Index} as an argument
     */
    public static boolean hasIndexParameter (String command) throws IllegalArgumentException {
        try {
            return Arrays.asList(commandsWithIndexes).contains(command);
        } catch (NullPointerException e) {
            return false;
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if {@code command} takes in a {@code Prefix} as an argument
     */
    public static boolean hasPrefixParameter (String command) throws IllegalArgumentException {
        try {
            return Arrays.asList(commandsWithPrefixes).contains(command);
        } catch (NullPointerException e) {
            return false;
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException();
        }
    }


}
