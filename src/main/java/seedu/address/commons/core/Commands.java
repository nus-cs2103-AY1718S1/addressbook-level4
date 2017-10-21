package seedu.address.commons.core;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearHistoryCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Container for all command words and command aliases in the application.
 */
public class Commands {

    private static String[] ALL_COMMAND_WORDS = {
        AddCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        ClearHistoryCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD
    };

    private static String[] ALL_COMMAND_ALIASES = {
        AddCommand.COMMAND_ALIAS,
        ClearCommand.COMMAND_ALIAS,
        ClearHistoryCommand.COMMAND_ALIAS,
        DeleteCommand.COMMAND_ALIAS,
        EditCommand.COMMAND_ALIAS,
        ExitCommand.COMMAND_ALIAS,
        FindCommand.COMMAND_ALIAS,
        HelpCommand.COMMAND_ALIAS,
        HistoryCommand.COMMAND_ALIAS,
        ListCommand.COMMAND_ALIAS,
        RedoCommand.COMMAND_ALIAS,
        SelectCommand.COMMAND_ALIAS,
        UndoCommand.COMMAND_ALIAS
    };

    public static String[] getAllCommandWords() {
        return ALL_COMMAND_WORDS;
    }

    public static String[] getAllCommandAliases() {
        return ALL_COMMAND_ALIASES;
    }

}
