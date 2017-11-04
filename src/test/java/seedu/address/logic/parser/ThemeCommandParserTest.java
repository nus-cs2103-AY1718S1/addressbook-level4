package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_DARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

//@@author cctdaniel
public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        assertParseSuccess(parser, VALID_THEME_LIGHT, new ThemeCommand("light"));
        assertParseSuccess(parser, VALID_THEME_DARK, new ThemeCommand("dark"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_THEME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }

}
