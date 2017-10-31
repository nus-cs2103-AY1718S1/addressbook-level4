package seedu.address.logic.autocomplete.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
import seedu.address.logic.autocomplete.CommandWordUsageTuple;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author john19950730
/**
 * Parses incomplete user input into list of possible command skeletons.
 */
public class AutoCompleteCommandParser implements AutoCompleteParser {

    private static final List<CommandWordUsageTuple> COMMAND_WORDS_LIST = Arrays.asList(new CommandWordUsageTuple[] {
        new CommandWordUsageTuple(AddCommand.COMMAND_WORD, AddCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(EditCommand.COMMAND_WORD, EditCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(FindCommand.COMMAND_WORD, FindCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(FindTagCommand.COMMAND_WORD, FindTagCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ListCommand.COMMAND_WORD, ListCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(RemarkCommand.COMMAND_WORD, RemarkCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(RemoveTagCommand.COMMAND_WORD, RemoveTagCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_USAGE)
    });

    /**
     * Generates a list of possible commands from the supplied user input stub
     * @param stub incomplete user input
     * @return list of possible commands determined from incomplete user input
     */
    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleCommands = new LinkedList<String>();

        possibleCommands.addAll(COMMAND_WORDS_LIST.stream()
                .filter(commandTuple -> AutoCompleteUtils.startWithSameLetters(stub, commandTuple.getCommandWord()))
                .map(commandTuple -> commandTuple.getCommandUsage())
                .collect(Collectors.toList()));
        possibleCommands.add(stub);

        return possibleCommands;
    }
}
