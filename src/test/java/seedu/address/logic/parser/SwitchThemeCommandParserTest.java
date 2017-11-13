package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SwitchThemeCommand;

//@@author ZhangH795

public class SwitchThemeCommandParserTest {
    private SwitchThemeCommandParser parser = new SwitchThemeCommandParser();

    @Test
    public void parseValidArgsReturnsSwitchThemeCommand() {
        String themeChoice1 = SwitchThemeCommand.DARK_THEME_WORD3;
        String themeChoice2 = SwitchThemeCommand.DARK_THEME_WORD1;
        String themeChoice3 = SwitchThemeCommand.DARK_THEME_WORD2;

        SwitchThemeCommand expectedSwitchThemeCommandOne =
                new SwitchThemeCommand(themeChoice1);
        assertParseSuccess(parser, themeChoice1, expectedSwitchThemeCommandOne);

        SwitchThemeCommand expectedSwitchThemeCommandTwo =
                new SwitchThemeCommand(themeChoice2);
        assertParseSuccess(parser, themeChoice2, expectedSwitchThemeCommandTwo);

        SwitchThemeCommand expectedSwitchThemeCommandThree =
                new SwitchThemeCommand(themeChoice3);
        assertParseSuccess(parser, themeChoice3, expectedSwitchThemeCommandThree);
    }

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchThemeCommand.MESSAGE_USAGE));
    }
}
