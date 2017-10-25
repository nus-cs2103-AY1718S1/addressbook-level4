package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.DeleteTagCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

/**
 * @author Sri-vatsa
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTagCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        //single entry
        String [] arg = new String[]{"friends"};
        assertParseSuccess(parser, "friends", new DeleteTagCommand(arg));

        //multiple entries
        String [] args = new String[] {"friends", "colleagues"};
        assertParseSuccess(parser, "friends colleagues", new DeleteTagCommand(args));

        //entries with space
        String [] argsWithSpace = new String[] {"friends", "colleagues"};
        assertParseSuccess(parser, "\n friends \n \t colleagues  \t", new DeleteTagCommand(argsWithSpace));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
    }
}