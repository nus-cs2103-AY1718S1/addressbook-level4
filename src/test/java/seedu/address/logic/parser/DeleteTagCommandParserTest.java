//@@author majunting
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand;


public class DeleteTagCommandParserTest {
    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parser_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ",
                MESSAGE_INVALID_COMMAND_FORMAT + DeleteTagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        DeleteTagCommand expectedDeleteTagCommand =
                new DeleteTagCommand("a");
        assertParseSuccess(parser, "a", expectedDeleteTagCommand);
    }
}
