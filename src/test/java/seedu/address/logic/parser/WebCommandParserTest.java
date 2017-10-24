package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.WebCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class WebCommandParserTest {

    private WebCommandParser parser = new WebCommandParser();

    @Test
    public void parse_validArgs_returnsWebCommand() {
        assertParseSuccess(parser, "maps", new WebCommand(WebCommandParser.WEBSITES_MAP.get("maps")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, WebCommand.MESSAGE_USAGE));
    }
}
