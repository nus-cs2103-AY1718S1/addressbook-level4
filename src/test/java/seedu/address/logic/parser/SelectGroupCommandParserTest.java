package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;

import org.junit.Test;

import seedu.address.logic.commands.SelectGroupCommand;

//@@author eldonng
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class SelectGroupCommandParserTest {
    private SelectGroupCommandParser parser = new SelectGroupCommandParser();

    @Test
    public void parse_validArgs_returnsSelectGroupCommand() {
        assertParseSuccess(parser, "1", new SelectGroupCommand(INDEX_FIRST_GROUP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectGroupCommand.MESSAGE_USAGE));
    }
}
