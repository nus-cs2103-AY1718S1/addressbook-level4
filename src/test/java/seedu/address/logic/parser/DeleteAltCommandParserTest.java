package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAltCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteAltCommand code. For example, inputs "abc" and "abc 1" take the
 * same path through the DeleteAltCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAltCommandParserTest {

    private DeleteAltCommandParser parser = new DeleteAltCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAltCommand() {
        assertParseSuccess(parser, "Alice Pauline", new DeleteAltCommand(NAME_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAltCommand.MESSAGE_USAGE));
    }
}
