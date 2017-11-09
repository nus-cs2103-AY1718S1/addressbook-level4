package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.QrCallCommand;
//@@author danielweide
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class QrCallCommandParserTest {

    private QrCallCommandParser parser = new QrCallCommandParser();

    @Test
    public void parse_validArgs_returnsQrCallCommand() {
        assertParseSuccess(parser, "1", new QrCallCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, QrCallCommand.MESSAGE_USAGE));
    }
}
