package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;

public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parseSubjectAbsentSuccess() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON, ""));
    }

    @Test
    public void parseSubjectPresentSuccess() {
        assertParseSuccess(parser, "1 s/hello", new EmailCommand(INDEX_FIRST_PERSON, "hello"));
    }

    @Test
    public void parseSubjectPresentWithSpacingSuccess() {
        assertParseSuccess(parser, "1 s/hello world", new EmailCommand(INDEX_FIRST_PERSON, "hello%20world"));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidPersonIndexThrowsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
