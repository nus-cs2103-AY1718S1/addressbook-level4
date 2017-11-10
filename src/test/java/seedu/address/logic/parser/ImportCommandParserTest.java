package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

//@@author LimeFallacie
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, "data\\circlesImportTest.xml", new ImportCommand("data\\circlesImportTest.xml"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
