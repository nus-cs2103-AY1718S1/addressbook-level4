//@@author fustilio
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FROZEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTagCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTagCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 *
 * Deprecated in Ark v1.5
 * See {@link DeleteTagCommand}
 */
public class DeleteTagCommandParserTest {

    private static final String INVALID_TAG = "#friend";
    private static final String VALID_TAG_1 = VALID_TAG_FROZEN;
    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() throws IllegalValueException {
        assertParseSuccess(parser, VALID_TAG_FROZEN, new DeleteTagCommand(Tag.getInstance(VALID_TAG_1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_TAG, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                              DeleteTagCommand.MESSAGE_USAGE));
    }
}
