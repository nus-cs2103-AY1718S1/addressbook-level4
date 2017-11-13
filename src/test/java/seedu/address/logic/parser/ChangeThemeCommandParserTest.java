//@@author ChenXiaoman
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

public class ChangeThemeCommandParserTest {

    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeThemeCommand() throws Exception {
        // no leading and trailing whitespaces
        ChangeThemeCommand expectedChangeThemeCommand = new ChangeThemeCommand("dark");
        assertParseSuccess(parser, "dark", expectedChangeThemeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  \t \n dark \n \t ", expectedChangeThemeCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));

    }

}
