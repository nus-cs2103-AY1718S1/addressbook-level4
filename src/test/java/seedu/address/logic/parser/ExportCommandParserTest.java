package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    //@@author stan789
    @Test
    public void parse_validArgs_returnsExportCommand() {
        String extension = ExportCommandParser.VCF_EXTENSION;
        String location = "src/test/data/VCardFileTest/new.vcf";
        String fileName = "new";
        assertParseSuccess(parser, location, new ExportCommand(location , fileName, extension));

    }

    //@@author stan789
    @Test
    public void parse_invalidArgs_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_DIRECTORY);

    }

    //@@author stan789
    @Test
    public void parse_invalidArgsWrongFileFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_EXTENSION);

    }

    //@@author stan789
    @Test
    public void parse_invalidArgsWrongFileNameFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/!new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_FILE_NAME);

    }

    //@@author stan789
    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

    }

}
