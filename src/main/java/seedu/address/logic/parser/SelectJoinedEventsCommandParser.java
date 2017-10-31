package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectJoinedEventsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author leonchowwenhao
/**
 * Parses input arguments and creates a new SelectJoinedEventsCommand object
 */
public class SelectJoinedEventsCommandParser implements Parser<SelectJoinedEventsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectJoinedEventsCommand
     * and returns an SelectJoinedEventsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectJoinedEventsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectJoinedEventsCommand.MESSAGE_USAGE));
        }
        String[] indexes = trimmedArgs.split("\\s+");
        try {
            List<Index> indexList = new ArrayList<>();
            for (String test : indexes) {
                Index index = ParserUtil.parseIndex(test);
                indexList.add(index);
            }
            return new SelectJoinedEventsCommand(indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectJoinedEventsCommand.MESSAGE_USAGE));
        }
    }
}
