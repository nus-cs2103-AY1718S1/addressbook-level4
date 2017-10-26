package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input for the group command and creates a new groupcommand object
 */

public class GroupCommandParser implements Parser<GroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public GroupCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        String[] groupKeyWords = trimmedArgs.split("\\s+");

        return new GroupCommand(makeNameList(groupKeyWords));
    }

    /**
     *
     * @param input array of arguments
     * @return just a list of the names in the argument
     */
    private List<String> makeNameList (String[] input) {
        List<String> nameList = new ArrayList<>();

        nameList.addAll(Arrays.asList(input));

        return nameList;
    }
}
