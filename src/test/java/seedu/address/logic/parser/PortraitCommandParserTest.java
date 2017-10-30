package seedu.address.logic.parser;

import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PORTRAIT_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.PORTRAIT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PortraitCommand;
import seedu.address.model.person.PortraitPath;

public class PortraitCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            PortraitCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INDEX = ParserUtil.MESSAGE_INVALID_INDEX;
    private PortraitCommandParser parser = new PortraitCommandParser();

    @Test
    public void testValidArgs() {
        PortraitCommand expectedCommand = null;
        try {
            expectedCommand = new PortraitCommand(Index.fromOneBased(1),
                    new PortraitPath(VALID_PORTRAIT_PATH_FIRST));
        } catch (IllegalValueException e) {
            fail("Test data cannot throw exception");
        }

        assertParseSuccess(parser, "1" + PORTRAIT_DESC_FIRST, expectedCommand);

        assertParseSuccess(parser, "1" + PORTRAIT_DESC_SECOND + PORTRAIT_DESC_FIRST, expectedCommand);
    }

    @Test
    public void testMissingPartsFailure() {

        assertParseFailure(parser, "" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "1 " + VALID_PORTRAIT_PATH_FIRST, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void testInvalidValueFailure() {
        // negative index
        assertParseFailure(parser, "-5" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 not an index", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/" + VALID_PORTRAIT_PATH_FIRST, MESSAGE_INVALID_FORMAT);
    }
}
