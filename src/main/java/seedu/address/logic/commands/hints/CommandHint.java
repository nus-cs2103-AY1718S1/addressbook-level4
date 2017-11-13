package seedu.address.logic.commands.hints;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Autocomplete;
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
import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShareCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Aliases;
import seedu.address.model.UserPrefs;

/**
 * Generates hint and autocompletion for command words
 */
public class CommandHint extends Hint {

    protected String commandWord;

    public CommandHint(String userInput, String commandWord) {
        this.userInput = userInput;
        this.commandWord = commandWord;
    }

    @Override
    public String autocomplete() {
        return userInput.trim() + argumentHint;
    }

    @Override
    public void parse() {
        String autocompleted = Autocomplete.autocompleteCommand(commandWord);

        if (autocompleted == null) {
            description = " type help for user guide";
            argumentHint = "";
        } else {
            argumentHint = StringUtil.difference(commandWord, autocompleted) + " ";
            description = getDescription(autocompleted);
        }
    }

    private static String getDescription(String commandWord) {

        Aliases aliases = UserPrefs.getInstance().getAliases();
        String aliasedCommand = aliases.getCommand(commandWord);

        commandWord = aliasedCommand != null ? aliasedCommand : commandWord;

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return "adds a person";
        case EditCommand.COMMAND_WORD:
            return "edits a person";
        case FindCommand.COMMAND_WORD:
            return "finds a person";
        case SelectCommand.COMMAND_WORD:
            return "selects a person";
        case DeleteCommand.COMMAND_WORD:
            return "deletes a person";
        case ShareCommand.COMMAND_WORD:
            return "shares a contact via email";
        case ClearCommand.COMMAND_WORD:
            return ClearCommandHint.CLEAR_COMMAND_DESC;
        case ListCommand.COMMAND_WORD:
            return ListCommandHint.LIST_COMMAND_DESC;
        case HistoryCommand.COMMAND_WORD:
            return HistoryCommandHint.HISTORY_COMMAND_DESC;
        case ExitCommand.COMMAND_WORD:
            return ExitCommandHint.EXIT_COMMAND_DESC;
        case UndoCommand.COMMAND_WORD:
            return UndoCommandHint.UNDO_COMMAND_DESC;
        case RedoCommand.COMMAND_WORD:
            return RedoCommandHint.REDO_COMMAND_DESC;
        case HelpCommand.COMMAND_WORD:
            return HelpCommandHint.HELP_COMMAND_DESC;
        case MusicCommand.COMMAND_WORD:
            return "plays music";
        case RadioCommand.COMMAND_WORD:
            return "plays the radio";
        case AliasCommand.COMMAND_WORD:
            return "sets or show alias";
        case UnaliasCommand.COMMAND_WORD:
            return "removes alias";
        default:
            return "";
        }
    }
}
