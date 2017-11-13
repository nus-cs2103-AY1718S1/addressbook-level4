package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author freesoup
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFileType_throwsParseException() {
        //only fileName
        assertParseFailure(parser, "output", ExportCommand.MESSAGE_WRONG_FILE_TYPE);

        //file without file extension
        assertParseFailure(parser, "output.", ExportCommand.MESSAGE_WRONG_FILE_TYPE);
    }



    @Test
    public void parse_validArgs_returnExportCommand() throws ParseException {
        //valid xml Export
        ExportCommand exportCommand = new ExportCommand("output.xml");
        assertTrue(parser.parse("output.xml") instanceof ExportCommand);
        assertParseSuccess(parser, "output.xml", exportCommand);

        //valid vcf Export
        ExportCommand exportCommand2 = new ExportCommand("output.vcf");
        assertTrue(parser.parse("output.vcf") instanceof ExportCommand);
        assertParseSuccess(parser, "output.vcf", exportCommand2);
    }
}
