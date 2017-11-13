package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.TreeSet;

import seedu.address.logic.commands.SuggestCommand;
import seedu.address.logic.commands.UniqueCommandList;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author CindyTsai1
/**
 * Suggest user input command
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    private UniqueCommandList list;

    /**
     * Parses the given {@code String} of commandWord in the context of the SuggestCommand
     * and returns an SuggestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format or if a suggested spelling is found
     */
    public SuggestCommand parse (String commandWord) throws ParseException {
        list = new UniqueCommandList();
        TreeSet<String> possibleCommand = list.getPossibleCommandList(commandWord);
        ArrayList<String> commandList = list.getCommandList();

        for (String command: possibleCommand) {
            if (commandList.contains(command)) {
                return new SuggestCommand(command);
            }
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
