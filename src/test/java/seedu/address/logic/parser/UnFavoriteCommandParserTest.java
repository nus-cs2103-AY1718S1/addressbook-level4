package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.UnFavoriteCommand;

//@@author keithsoc
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnFavoriteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnFavoriteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnFavoriteCommandParserTest {

    private UnFavoriteCommandParser parser = new UnFavoriteCommandParser();

    @Test
    public void parse_validArgs_returnsUnFavoriteCommand() {
        assertParseSuccess(parser, "1", new UnFavoriteCommand(Arrays.asList(INDEX_FIRST_PERSON)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnFavoriteCommand.MESSAGE_USAGE));
    }
}
//@@author
