package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.UnbanCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnbanCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnbanCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnbanCommandParserTest {
    private UnbanCommandParser parser = new UnbanCommandParser();

    @Test
    public void parse_validArgs_returnsUnBanCommand() {
        assertParseSuccess(parser, "1", new UnbanCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnbanCommand.MESSAGE_USAGE));
    }
}
