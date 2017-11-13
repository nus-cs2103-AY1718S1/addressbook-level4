package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TestUtil.getFilePathInDataFolder;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.NewCommand;

//@@author chrisboo
public class NewCommandParserTest {

    private static String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE);

    private NewCommandParser parser = new NewCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no path specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPath_failure() {
        // path is a directory
        assertParseFailure(parser, getFilePathInDataFolder(""), MESSAGE_INVALID_FORMAT);

        // path is not a DeathNote file
        assertParseFailure(parser, getFilePathInDataFolder("random.file"), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        String fileName = "sampleData.xml";
        String filePath = getFilePathInDataFolder(fileName);
        assertParseSuccess(parser, filePath, new NewCommand(new File(filePath)));
    }
}
//@@author
