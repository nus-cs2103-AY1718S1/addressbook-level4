package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.MassEmailCommand;
import seedu.address.model.tag.TagMatchingPredicate;

//@@author ReneeSeet

public class MassEmailCommandParserTest {

    private static final String STUB_TAG_VALID_FIRST = "owesMoney";
    private static final String STUB_TAG_VALID_SECOND = "friends";
    private MassEmailParser parser = new MassEmailParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMassEmailCommand() {
        // no leading and trailing whitespaces
        MassEmailCommand expectedMassEmailCommand =
                new MassEmailCommand(new TagMatchingPredicate((
                        Arrays.asList(STUB_TAG_VALID_FIRST , STUB_TAG_VALID_SECOND))));
        assertParseSuccess(parser, STUB_TAG_VALID_FIRST + " " + STUB_TAG_VALID_SECOND , expectedMassEmailCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + STUB_TAG_VALID_FIRST
                + " \n \t " + STUB_TAG_VALID_SECOND + "  \t", expectedMassEmailCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassEmailCommand.MESSAGE_USAGE));
    }
}
