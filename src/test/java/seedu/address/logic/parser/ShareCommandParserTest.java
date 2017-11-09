package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ShareCommand;

//@@author hanselblack
public class ShareCommandParserTest {

    private ShareCommandParser parser = new ShareCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
    }
}
