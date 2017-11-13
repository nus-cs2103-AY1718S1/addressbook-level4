package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.OpenRolodexCommand;

public class OpenRolodexCommandParserTest {

    private OpenRolodexCommandParser parser = new OpenRolodexCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNonEmptyArgInvalidFilePathThrowsParseException() {
        // needs a root directory as reference for anchored paths (e.g. ./x.rldx)
        assertParseFailure(parser, "default.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFilePathAllowsSpacing() {
        // spacing should be allowed for directories
        String filePathWithSpacing = "directory with/some spacings/default.rldx";
        OpenRolodexCommand expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);

        filePathWithSpacing = "56 75/22 3456g 45642345/y5/ bhh3 57y357/ 65467y5 - 0 o1.rldx";
        expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);
    }

    @Test
    public void parseFilePathSwitchesBackslashes() {
        // backslashes are swapped out for forward slashes in directory
        String filePathWithBackSlashes = "C:\\Users\\Rolodex\\Downloads\\default.rldx";
        String filePathWithForwardSlashes = "C:/Users/Rolodex/Downloads/default.rldx";

        OpenRolodexCommand expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithForwardSlashes);
        assertParseSuccess(parser, filePathWithBackSlashes, expectedOpenRolodexCommand);
    }
}
