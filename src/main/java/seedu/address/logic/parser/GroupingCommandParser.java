//@@author hthjthtrh
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input argument and creates a new GroupingCommand object
 */
public class GroupingCommandParser implements Parser<GroupingCommand> {

    public static final String MESSAGE_INCORRECT_GROUPNAME_FORMAT = "Group name cannot be a integer!";

    /**
     * Parses the given {@code String} of arguments in the context of the GroupingCommand
     * and returns an GroupingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GroupingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        List<String> argsList = Arrays.asList(args.split(" "));

        String grpName;
        List<String> indStrList;

        boolean isInteger;
        if (argsList.size() >= 2) {
            // check if group name is an integer
            try {
                ParserUtil.parseInt(argsList.get(0));
                isInteger = true;
            } catch (IllegalValueException e) {
                isInteger = false;
            }

            // if group name is integer, alert user
            if (isInteger) {
                throw new ParseException(MESSAGE_INCORRECT_GROUPNAME_FORMAT, GroupingCommand.MESSAGE_USAGE);
            }
            grpName = argsList.get(0);
            indStrList = argsList.subList(1, argsList.size());
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
        }

        // using hashset to eliminate any duplicates
        Set<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indStrList) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
            }
        }

        List<Index> indexList = new ArrayList<>();

        indexIntsSet.forEach(idx -> indexList.add(Index.fromOneBased(idx)));

        return new GroupingCommand(grpName, indexList);
    }
}
//@@author
