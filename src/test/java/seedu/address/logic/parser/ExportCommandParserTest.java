package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_CREATE_NEW_FOLDER;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_DOCS;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_LOCAL_D_DRIVE;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ExportCommand code. For example, inputs "docs/ab.xml" and "docs /ab.xml" take the
 * same path through the ExportCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, "docs/AcquaiNote.xml", new ExportCommand(FILE_PATH_DOCS));
        assertParseSuccess(parser, "D:\\AcquaiNote.xml", new ExportCommand(FILE_PATH_LOCAL_D_DRIVE));
        assertParseSuccess(parser, "C:\\shalkalaka\\AcquaiNote.xml",
            new ExportCommand(FILE_PATH_CREATE_NEW_FOLDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "docs/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "docs/AcquaiNote", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "C/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "docs/ab.xmla", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }
}
