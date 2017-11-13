package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.PhotoCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PhotoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the PhotoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
//@@author RonakLakhotia
public class PhotoCommandParserTest {

    private PhotoCommandParser parser = new PhotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        String FilePath = "src/main/resources/images/" + "clock" + ".png";

        assertParseSuccess(parser, "1 src/main/resources/images/clock.png",
                new PhotoCommand(INDEX_FIRST_PERSON, FilePath));
    }


}
