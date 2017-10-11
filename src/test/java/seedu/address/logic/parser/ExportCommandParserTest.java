package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static org.junit.Assert.*;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_wrongFileType() {
        assertParseFailure(parser, "export .pdf C:/", MESSAGE_FILE_TYPE_NOT_SUPPORTED);
    }

    @Test
    public void parse_missingArguments() {
        assertParseFailure(parser, "export C:/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}

