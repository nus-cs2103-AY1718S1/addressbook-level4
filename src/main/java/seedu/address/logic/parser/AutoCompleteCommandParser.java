package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.address.logic.autocomplete.CommandWordUsageTuple;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Parses incomplete user input into list of possible command skeletons.
 */
public class AutoCompleteCommandParser {

    private static final List<CommandWordUsageTuple> COMMAND_WORDS_LIST = Arrays.asList(new CommandWordUsageTuple[] {
        new CommandWordUsageTuple(AddCommand.COMMAND_WORD, AddCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(EditCommand.COMMAND_WORD, EditCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(FindCommand.COMMAND_WORD, FindCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(ListCommand.COMMAND_WORD, ListCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(RemoveTagCommand.COMMAND_WORD, RemoveTagCommand.COMMAND_USAGE),
        new CommandWordUsageTuple(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD),
        new CommandWordUsageTuple(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD)
    });

    /**
     * Generates a list of possible commands from the supplied user input stub
     * @param stub incomplete user input
     * @return list of possible commands determined from incomplete user input
     */
    public List<String> parseForCommands(String stub) {
        final LinkedList<String> possibleCommands = new LinkedList<String>();
        // empty string will match everything,
        // short circuit method to prevent greedy matching
        if (stub.equals("")) {
            possibleCommands.add(stub);
            return possibleCommands;
        }

        for (CommandWordUsageTuple commandTuple : COMMAND_WORDS_LIST) {
            if (startWithSameLetters(stub, commandTuple.getCommandWord())) {
                possibleCommands.add(commandTuple.getCommandUsage());
            }
        }

        possibleCommands.add(stub);

        return possibleCommands;
    }

    /**
     * Checks if the command word starts with the letters of the incomplete command stub provided
     * @param stub incomplete command supplied by the user
     * @param commandWord {@code COMMAND_WORD} constant specified in each command class
     * @return true if commandWord contains stub as the first few letters
     */
    private boolean startWithSameLetters(String stub, String commandWord) {
        if (stub.length() <= commandWord.length()) {
            return stub.equals(commandWord.substring(0, stub.length()));
        } else {
            return false;
        }
    }

}
