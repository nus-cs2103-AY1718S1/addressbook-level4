package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TestUtil.getFilePathInSandboxFolder;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;

//@@author chrisboo
public class OpenCommandParserTest {

    private static String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE);

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no path specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPath_failure() {
        // path is a directory
        assertParseFailure(parser, getFilePathInSandboxFolder(""), MESSAGE_INVALID_FORMAT);

        // path does not exists
        assertParseFailure(parser, getFilePathInSandboxFolder("nonExistingFile.xml"), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        String filePath = getFilePathInSandboxFolder("sampleData.xml");
        assertParseSuccess(parser, filePath, new OpenCommand(new File(filePath)));
    }
}
//@@author
