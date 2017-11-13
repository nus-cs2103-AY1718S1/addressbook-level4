package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ColorKeywordCommand;

//@@author caoliangnus
public class ColorKeywordCommandParserTest {
    private ColorKeywordCommandParser parser = new ColorKeywordCommandParser();

    @Test
    public void parse_validArgs_returnsColorCommand() {
        assertParseSuccess(parser, "enable", new ColorKeywordCommand("enable"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "enabled",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColorKeywordCommand.MESSAGE_USAGE));
    }
}
