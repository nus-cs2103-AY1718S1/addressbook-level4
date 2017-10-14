package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {

    public static final String INPUT_LIGHT = "light";
    public static final String INPUT_DARK = "dark";

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void execute_themeChangeSuccess() {
        // Testing for light theme
        ThemeCommand expectedCommand = new ThemeCommand(ThemeCommand.LIGHT_THEME);
        assertParseSuccess(parser, INPUT_LIGHT, expectedCommand);

        // Testing for dark theme
        expectedCommand = new ThemeCommand(ThemeCommand.DARK_THEME);
        assertParseSuccess(parser, INPUT_DARK, expectedCommand);

    }
}
