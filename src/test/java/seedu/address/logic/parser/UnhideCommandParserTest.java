package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.person.UnhideCommand;
import seedu.address.logic.parser.person.UnhideCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnhideCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnhideCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class UnhideCommandParserTest {

    private UnhideCommandParser parser = new UnhideCommandParser();

    @Test
    public void parse_validArgs_returnsHideCommand() {
        assertParseSuccess(parser, "1", new UnhideCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnhideCommand.MESSAGE_USAGE));
    }


}
