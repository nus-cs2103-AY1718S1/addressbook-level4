package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_PARAMS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

//@@author kaiyu92
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_BOOK_PARAMS,
                ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no leading and trailing whitespaces
        ExportCommand expectedExportCommand =
                new ExportCommand("addressbook");
        assertParseSuccess(parser, "addressbook", expectedExportCommand);
    }
}
