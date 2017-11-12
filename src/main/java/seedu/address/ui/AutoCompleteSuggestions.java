package seedu.address.ui;

import java.util.Comparator;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
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
import seedu.address.logic.commands.ShareCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateUserCommand;
import seedu.address.logic.commands.WebCommand;

//@@author hansiang93

/**
 * Handles all command suggestions and their usage examples.
 */
public class AutoCompleteSuggestions {

    private static SuggestionProvider<String> suggestionList = new SuggestionProvider<String>() {
        @Override
        protected Comparator<String> getComparator() {
            return null;
        }

        @Override
        protected boolean isMatch(String suggestion, AutoCompletionBinding.ISuggestionRequest request) {
            return (suggestion.startsWith(request.getUserText()) && !suggestion.equals(request.getUserText()));
        }
    };

    static {
        suggestionList.addPossibleSuggestions(AddCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(AddCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(EditCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(EditCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(SelectCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(SelectCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(DeleteCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(DeleteCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ClearCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(FindCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(FindCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(FilterCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(FilterCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ListCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(SortCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(SelectCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(SortCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(HistoryCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(ExitCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(HelpCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(UndoCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(RedoCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(DeleteTagCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(DeleteTagCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(RemarkCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(RemarkCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(UpdateUserCommand.COMMAND_WORD);
        suggestionList.addPossibleSuggestions(ShareCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(UpdateUserCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(WebCommand.COMMAND_WORD);
        //suggestionList.addPossibleSuggestions(WebCommand.MESSAGE_USAGE_EXAMPLE);
        suggestionList.addPossibleSuggestions(ChangeThemeCommand.COMMAND_WORD);
    }

    public static SuggestionProvider<String> getSuggestionList() {
        return suggestionList;
    }
}
