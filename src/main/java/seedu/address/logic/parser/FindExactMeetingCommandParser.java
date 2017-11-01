package seedu.address.logic.parser;

import seedu.address.logic.commands.FindExactMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingContainsFullWordPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindExactMeetingCommandParser implements Parser<FindExactMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExactMeetingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExactMeetingCommand.MESSAGE_USAGE));
        }

        return new FindExactMeetingCommand(new MeetingContainsFullWordPredicate(Arrays.asList(trimmedArgs)));
    }
}
