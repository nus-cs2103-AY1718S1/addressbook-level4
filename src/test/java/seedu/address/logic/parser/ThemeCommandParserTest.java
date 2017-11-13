package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_THEME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

//@@author chuaweiwen
public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_existingArgs_returnsThemeCommand() throws Exception {
        ThemeCommand expectedCommand = new ThemeCommand(new Theme(ThemeList.THEME_NIGHT, ThemeList.THEME_NIGHT_PATH));
        assertParseSuccess(parser, ThemeList.THEME_NIGHT, expectedCommand);
    }

    @Test
    public void parse_nonExistingArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "unknown_theme", String.format(MESSAGE_UNKNOWN_THEME,
                ThemeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ThemeCommand.MESSAGE_USAGE));
    }
}
//@@author
