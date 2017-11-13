//@@author HanYaodong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private static final String VALID_FILE_PATH = "TestFile.xml";
    private static final String EXPECTED_MATCH_ERROR_MESSAGE =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private static final String EXPECTED_FILE_MISSING_ERROR_MESSAGE = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT, ExportCommandParser.MISSING_FILE_PATH + ExportCommand.MESSAGE_USAGE);
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_missingSemicolon_failure() {
        String input = "1 " + VALID_FILE_PATH;
        assertParseFailure(parser, input, EXPECTED_MATCH_ERROR_MESSAGE);
    }

    @Test
    public void parse_missingIndex_failure() {
        String input = " ; " + VALID_FILE_PATH;
        assertParseFailure(parser, input, EXPECTED_MATCH_ERROR_MESSAGE);
    }

    @Test
    public void parse_missingFilePath_failure() {
        String input = "1 ; ";
        assertParseFailure(parser, input, EXPECTED_FILE_MISSING_ERROR_MESSAGE);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String input = "1, 2 3-4 a; " + VALID_FILE_PATH;
        assertParseFailure(parser, input, EXPECTED_MATCH_ERROR_MESSAGE);

        String negativeIndexInput = "-1 ; " + VALID_FILE_PATH;
        assertParseFailure(parser, negativeIndexInput, EXPECTED_MATCH_ERROR_MESSAGE);
    }

    @Test
    public void parse_filePathWithInvalidExtension_successWithModifiedFilePath() {
        String xmlExtension = ".xml";
        String validIndexList = "1, 2;";
        List<Index> validIndexes = getIndexListFromOneBasedArray(1, 2);

        // no extension -> add .xml to filePath
        String filePathWithoutExtension = "TestFile";
        assertParseSuccess(parser, validIndexList + filePathWithoutExtension,
            new ExportCommand(validIndexes, filePathWithoutExtension + xmlExtension));

        // wrong extension -> add .xml to filePath
        String filePathWithWrongExtension = "TestFile.txt";
        assertParseSuccess(parser, validIndexList + filePathWithWrongExtension,
            new ExportCommand(validIndexes, filePathWithWrongExtension + xmlExtension));

        // */.xml -> add .xml to filePath
        String filePathWithoutFileName = "./.xml";
        assertParseSuccess(parser, validIndexList + filePathWithoutFileName,
            new ExportCommand(validIndexes, filePathWithoutFileName + xmlExtension));
    }

    @Test
    public void parse_validArgs_success() {
        List<Index> indexes = getIndexListFromOneBasedArray(1, 2, 3);
        String input = "1, 2 3 ; " + VALID_FILE_PATH;
        ExportCommand exportCommand = new ExportCommand(indexes, VALID_FILE_PATH.trim());
        assertParseSuccess(parser, input, exportCommand);

        indexes = getIndexListFromOneBasedArray(1, 2, 3, 5, 7, 8, 9);
        input = "1, 2, 3 5 7-9;" + VALID_FILE_PATH;
        exportCommand = new ExportCommand(indexes, VALID_FILE_PATH.trim());
        assertParseSuccess(parser, input, exportCommand);
    }

    /**
     * Converts an One-based {@link Integer} Array to an {@link Index} List.
     */
    private List<Index> getIndexListFromOneBasedArray(Integer... integers) {
        return Arrays.stream(integers).map(Index::fromOneBased).collect(Collectors.toList());
    }
}
