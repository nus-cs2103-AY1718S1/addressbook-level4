package seedu.address.ui;

import java.util.ArrayList;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateUserCommand;
import seedu.address.logic.commands.WebCommand;

/**
 * Handles all command suggestions and their usage examples.
 */
public class AutoCompleteSuggestions {
    private static ArrayList<String> suggestionList = new ArrayList<>();

    static {
        suggestionList.add(AddCommand.COMMAND_WORD);
        suggestionList.add(AddCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(EditCommand.COMMAND_WORD);
        suggestionList.add(EditCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(SelectCommand.COMMAND_WORD);
        suggestionList.add(SelectCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(DeleteCommand.COMMAND_WORD);
        suggestionList.add(DeleteCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(ClearCommand.COMMAND_WORD);
        suggestionList.add(FindCommand.COMMAND_WORD);
        suggestionList.add(FindCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(FilterCommand.COMMAND_WORD);
        suggestionList.add(FilterCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(ListCommand.COMMAND_WORD);
        suggestionList.add(SortCommand.COMMAND_WORD);
        suggestionList.add(SortCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(HistoryCommand.COMMAND_WORD);
        suggestionList.add(ExitCommand.COMMAND_WORD);
        suggestionList.add(HelpCommand.COMMAND_WORD);
        suggestionList.add(UndoCommand.COMMAND_WORD);
        suggestionList.add(RedoCommand.COMMAND_WORD);
        suggestionList.add(DeleteTagCommand.COMMAND_WORD);
        suggestionList.add(DeleteTagCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(RemarkCommand.COMMAND_WORD);
        suggestionList.add(RemarkCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(UpdateUserCommand.COMMAND_WORD);
        suggestionList.add(UpdateUserCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.add(WebCommand.COMMAND_WORD);
        suggestionList.add(WebCommand.MESSAGE_USAGE_EXAMPLE);
    }

    public static ArrayList<String> getSuggestionList() {
        return suggestionList;
    }
}
