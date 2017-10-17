package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RetrieveCommand;

public class RetrieveCommandParserTest {

    private RetrieveCommandParser parser = new RetrieveCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        final String expectedMessage = String.format(RetrieveCommand.MESSAGE_EMPTY_ARGS, RetrieveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsRetrieveCommand() {
        // no leading and trailing whitespaces
        RetrieveCommand expectedCommand =
                new RetrieveCommand("friends");
        assertParseSuccess(parser, "friends", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n friends \t \n", expectedCommand);
    }

}
