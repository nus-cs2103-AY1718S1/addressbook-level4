//@@author ChenXiaoman
package seedu.address.logic.parser;

import static seedu.address.logic.commands.ChangeFontSizeCommand.DECREASE_FONT_SIZE_COMMAND;
import static seedu.address.logic.commands.ChangeFontSizeCommand.INCREASE_FONT_SIZE_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.font.FontSize.FONT_SIZE_L_LABEL;
import static seedu.address.model.font.FontSize.FONT_SIZE_XL_LABEL;
import static seedu.address.model.font.FontSize.FONT_SIZE_XS_LABEL;

import org.junit.Test;

import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.model.font.FontSize;

public class ChangeFontSizeCommandParserTest {
    private ChangeFontSizeCommandParser parser = new ChangeFontSizeCommandParser();

    @Test
    public void parseSuccess() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = new ChangeFontSizeCommand(FONT_SIZE_L_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + FONT_SIZE_L_LABEL;
        assertParseSuccess(parser, userInput, changeFontSizeCommand);
    }

    @Test
    public void parseInvalidFontSizeFailure() throws Exception {
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + INVALID_FONT_SIZE;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
    }

    @Test
    public void parseIncreaseFailure() throws Exception {
        FontSize.setCurrentFontSizeLabel(FONT_SIZE_XL_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + INCREASE_FONT_SIZE_COMMAND;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_IS_LARGEST);
    }

    @Test
    public void parseDecreaseFailure() throws Exception {
        FontSize.setCurrentFontSizeLabel(FONT_SIZE_XS_LABEL);
        String userInput = ChangeFontSizeCommand.COMMAND_WORD + " " + DECREASE_FONT_SIZE_COMMAND;
        assertParseFailure(parser, userInput, FontSize.MESSAGE_FONT_SIZE_IS_SMALLEST);
    }

}
