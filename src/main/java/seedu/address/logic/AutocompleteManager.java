//@@author namvd2709
package seedu.address.logic;

import java.util.ArrayList;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterAllCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UngroupCommand;

/**
 * Manage autocomplete logic when typing commands
 */
public class AutocompleteManager {

    // This should include all commands.
    // Add new commands here if implemented
    private String[] commands = new String[]{
        AddCommand.COMMAND_WORD, AppointCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
        GroupCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, UngroupCommand.COMMAND_WORD,
        RemoveTagCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD, FilterAllCommand.COMMAND_WORD
    };

    public AutocompleteManager() {}

    //@@author namvd2709
    /**
     * attempt to autocomplete input into one of the commands
     * @param matcher field input
     * @return String representation of command, or the matcher if not exactly one command is returned.
     */
    public String attemptAutocomplete(String matcher) {
        ArrayList<String> matches = new ArrayList<>();
        for (String command: commands) {
            if (StringUtil.containsWordsStartWithString(command, matcher)) {
                matches.add(command);
            }
        }
        if (matches.size() == 1) {
            return matches.get(0);
        } else {
            return matcher;
        }
    }
}
