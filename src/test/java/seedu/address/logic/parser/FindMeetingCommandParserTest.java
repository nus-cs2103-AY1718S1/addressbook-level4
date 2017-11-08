package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindMeetingCommand;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;

public class FindMeetingCommandParserTest {

    private FindMeetingCommandParser parser = new FindMeetingCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindMeetingCommand expectedFindMeetingCommand =
                new FindMeetingCommand(new MeetingContainsKeywordsPredicate(Arrays.asList("Lunch", "Dinner")));
        assertParseSuccess(parser, "Lunch Dinner", expectedFindMeetingCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Lunch \n \t Dinner  \t", expectedFindMeetingCommand);
    }

}
