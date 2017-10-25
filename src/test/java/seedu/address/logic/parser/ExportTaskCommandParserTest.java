package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ExportTaskCommand;


public class ExportTaskCommandParserTest {

    private ExportTaskCommandParser parser = new ExportTaskCommandParser();

    @Test
    public void parseValidArgsReturnsExportCommand() {
        assertParseSuccess(parser, "1", new ExportTaskCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportTaskCommand.MESSAGE_USAGE));
    }

}
