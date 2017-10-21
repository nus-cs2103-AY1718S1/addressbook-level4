package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;

public class OpenCommandParserTest {

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNonEmptyArgInvalidFilePathThrowsParseException() {
        // needs a root directory as reference for anchored paths (e.g. ./x.rldx)
        assertParseFailure(parser, "default.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFilePathAllowsSpacing() {
        // spacing should be allowed for directories
        String filePathWithSpacing = "directory with/some spacings/default.rldx";
        OpenCommand expectedOpenCommand = new OpenCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenCommand);

        filePathWithSpacing = "56 75/22 3456g 45642345/y5/ bhh3 57y357/ 65467y5 - 0 o1";
        expectedOpenCommand = new OpenCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenCommand);
    }

    @Test
    public void parseFilePathSwitchesBackslashes() {
        // backslashes are swapped out for forward slashes in directory
        String filePathWithBackSlashes = "C:\\Users\\Rolodex\\Downloads\\default.rldx";
        String filePathWithForwardSlashes = "C:/Users/Rolodex/Downloads/default.rldx";

        OpenCommand expectedOpenCommand = new OpenCommand(filePathWithForwardSlashes);
        assertParseSuccess(parser, filePathWithBackSlashes, expectedOpenCommand);
    }
}
