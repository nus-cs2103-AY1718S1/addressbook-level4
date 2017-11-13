package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FaceBookCommand;
//@@author RonakLakhotia
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FacebookCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FacebookCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class FaceBookCommandParserTest {

    private FaceBookCommandParser parser = new FaceBookCommandParser();

    @Test
    public void parse_validArgs_returnsFaceBookCommand() {

        assertParseSuccess(parser, "1", new FaceBookCommand(INDEX_FIRST_PERSON));

        /* multiple whitespaces */
        assertParseSuccess(parser, "   1   ", new FaceBookCommand(INDEX_FIRST_PERSON));


        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FaceBookCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE
        ));

    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {

        assertParseFailure(parser, "1 2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE
        ));
    }
}
