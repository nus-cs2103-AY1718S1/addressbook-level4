package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

public class ChangePasswordCommandParserTest {

    private ChangePasswordCommandParser parser = new ChangePasswordCommandParser();

    @Test
    public void parse_invalidArgs_returnsChangePasswordCommand() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT));
    }
}
