package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

//@@author JasmineSee
public class ChangeThemeCommandParserTest {
    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();
    private String validTheme = "white";

    @Test
    public void parse_validArgs_returnsChangeThemeCommand() {
        ChangeThemeCommand expectedThemeCommand =
                new ChangeThemeCommand(validTheme);
        assertParseSuccess(parser, "white", expectedThemeCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() { //checks for unavailable colour themes
        assertParseFailure(parser, "blue",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
    }
}
