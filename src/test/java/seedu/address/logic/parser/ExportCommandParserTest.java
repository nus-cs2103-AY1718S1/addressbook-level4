package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ExportCommandParser.EXPORT_FILE_EXTENSION;
import static seedu.address.logic.parser.ExportCommandParser.MESSAGE_INVALID_EXTENSION;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

//@@author k-l-a
public class ExportCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private static final String MESSAGE_BAD_EXTENSION = String.format(MESSAGE_INVALID_EXTENSION, EXPORT_FILE_EXTENSION);

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "  ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "backup", MESSAGE_BAD_EXTENSION);
        assertParseFailure(parser, "backup.wrongextension", MESSAGE_BAD_EXTENSION);
    }

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "test.xml", new ExportCommand("test.xml"));
    }
}
