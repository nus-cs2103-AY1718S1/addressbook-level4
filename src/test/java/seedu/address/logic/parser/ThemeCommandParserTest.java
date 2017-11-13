package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

//@@author teclu
public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();
    private String[] listThemes = { "Light", "Dark", "Red", "Blue", "Green" };

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        for (int i = 0; i < 5; i++) {
            assertParseSuccess(parser, listThemes[i], new ThemeCommand(listThemes[i]));
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty Argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
