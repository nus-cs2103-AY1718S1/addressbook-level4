package seedu.address.commons.core;

import java.util.HashMap;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearHistoryCommand;
import seedu.address.logic.commands.ColourTagCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Container for all command words, command aliases, and shortened command usage in the application.
 */
public class Commands {
    private static String[] ALL_COMMAND_WORDS = {
        AddCommand.COMMAND_WORD,
        AddRelationshipCommand.COMMAND_WORD,
        BackupCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        ClearHistoryCommand.COMMAND_WORD,
        ColourTagCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        RemarkCommand.COMMAND_WORD,
        RemoveTagCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        SortCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD
    };

    private static String[] ALL_COMMAND_ALIASES = {
        AddCommand.COMMAND_ALIAS,
        AddRelationshipCommand.COMMAND_ALIAS,
        BackupCommand.COMMAND_ALIAS,
        ClearCommand.COMMAND_ALIAS,
        ClearHistoryCommand.COMMAND_ALIAS,
        ColourTagCommand.COMMAND_ALIAS,
        DeleteCommand.COMMAND_ALIAS,
        EditCommand.COMMAND_ALIAS,
        ExitCommand.COMMAND_ALIAS,
        FindCommand.COMMAND_ALIAS,
        HelpCommand.COMMAND_ALIAS,
        HistoryCommand.COMMAND_ALIAS,
        ListCommand.COMMAND_ALIAS,
        RedoCommand.COMMAND_ALIAS,
        RemarkCommand.COMMAND_ALIAS,
        SelectCommand.COMMAND_ALIAS,
        SortCommand.COMMAND_ALIAS,
        UndoCommand.COMMAND_ALIAS
    };

    private static String[] ALL_SHORT_MESSAGE_USAGES = {
        AddCommand.SHORT_MESSAGE_USAGE,
        AddRelationshipCommand.SHORT_MESSAGE_USAGE,
        BackupCommand.SHORT_MESSAGE_USAGE,
        ClearCommand.SHORT_MESSAGE_USAGE,
        ClearHistoryCommand.SHORT_MESSAGE_USAGE,
        ColourTagCommand.SHORT_MESSAGE_USAGE,
        DeleteCommand.SHORT_MESSAGE_USAGE,
        EditCommand.SHORT_MESSAGE_USAGE,
        ExitCommand.SHORT_MESSAGE_USAGE,
        FindCommand.SHORT_MESSAGE_USAGE,
        HelpCommand.SHORT_MESSAGE_USAGE,
        HistoryCommand.SHORT_MESSAGE_USAGE,
        ListCommand.SHORT_MESSAGE_USAGE,
        RedoCommand.SHORT_MESSAGE_USAGE,
        RemarkCommand.SHORT_MESSAGE_USAGE,
        RemoveTagCommand.SHORT_MESSAGE_USAGE,
        SelectCommand.SHORT_MESSAGE_USAGE,
        SortCommand.SHORT_MESSAGE_USAGE,
        UndoCommand.SHORT_MESSAGE_USAGE
    };

    private static HashMap<String, String> ALL_COMMANDS_AND_SHORT_MESSAGES;
    static {
        ALL_COMMANDS_AND_SHORT_MESSAGES = new HashMap<>();

        for (int i = 0; i < ALL_COMMAND_WORDS.length; i++) {
            ALL_COMMANDS_AND_SHORT_MESSAGES.put(ALL_COMMAND_WORDS[i], ALL_SHORT_MESSAGE_USAGES[i]);
        }
    }

    public static String[] getAllCommandWords() { return ALL_COMMAND_WORDS; }

    public static String[] getAllCommandAliases() {
        return ALL_COMMAND_ALIASES;
    }

    public static HashMap<String, String> getAllCommandUsages() { return ALL_COMMANDS_AND_SHORT_MESSAGES; }
}
