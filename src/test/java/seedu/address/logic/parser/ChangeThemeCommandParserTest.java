package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_THEME_NOT_FOUND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;
//@@author kosyoz
public class ChangeThemeCommandParserTest {

    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArg_throwParseException() {
        assertParseFailure(parser, "YellowTheme", MESSAGE_THEME_NOT_FOUND);
    }
    @Test
    public void parse_validArg_returnsChangeThemeCommand() {
        ChangeThemeCommand expectedChangeThemeCommand = new ChangeThemeCommand("RedTheme");
        assertParseSuccess(parser, "RedTheme", expectedChangeThemeCommand);
    }

}
