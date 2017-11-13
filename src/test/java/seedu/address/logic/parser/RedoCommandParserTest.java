package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RedoCommand.INDEX_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.EmailCommandParserTest.ALPHABET_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;

//@@author justintkj
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MapCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MapCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RedoCommandParserTest {

    public static final int ONE_REDO = 1;
    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() {
        assertParseSuccess(parser, INDEX_ONE, new RedoCommand(ONE_REDO));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RedoCommand.MESSAGE_USAGE));
    }
}
