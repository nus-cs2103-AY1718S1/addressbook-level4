package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SmsCommand;
import seedu.address.model.tag.TagMatchingPredicate;

//@@author zameschua
public class SmsCommandParserTest {

    private static final String STUB_TAG_VALID_FIRST = "owesMoney";
    private static final String STUB_TAG_VALID_SECOND = "classA";

    private SmsCommandParser parser = new SmsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSmsCommand() {
        SmsCommand expectedSmsCommand =
                new SmsCommand(new TagMatchingPredicate((Arrays.asList(STUB_TAG_VALID_FIRST, STUB_TAG_VALID_SECOND))));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, STUB_TAG_VALID_FIRST + " " + STUB_TAG_VALID_SECOND, expectedSmsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n " + STUB_TAG_VALID_FIRST + " \n \t " + STUB_TAG_VALID_SECOND + "  \t",
                expectedSmsCommand);
    }
}
