package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.QrSmsCommand;
//@@author danielweide
/**
 * Test scope: similar to {@code QrCallCommandParserTest}.
 * @see QrCallCommandParserTest
 */
public class QrSmsCommandParserTest {

    private QrSmsCommandParser parser = new QrSmsCommandParser();

    @Test
    public void parse_validArgs_returnsQrSmsCommand() {
        assertParseSuccess(parser, "1", new QrSmsCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrSmsCommand.MESSAGE_USAGE));
    }
}
