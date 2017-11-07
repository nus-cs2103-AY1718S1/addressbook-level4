package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;

import org.junit.Test;

import seedu.address.logic.commands.SwitchThemeCommand;

//@@author zhangshuoyang
public class SwitchThemeCommandParserTest {

    private SwitchThemeCommandParser parser = new SwitchThemeCommandParser();

    @Test
    public void parse_validArgs_returnsSwitchThemeCommand() {
        assertParseSuccess(parser, "1", new SwitchThemeCommand(INDEX_FIRST_THEME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SwitchThemeCommand.MESSAGE_USAGE));
    }
}
