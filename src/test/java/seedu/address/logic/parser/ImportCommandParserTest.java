//@@author HanYaodong
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParserException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        parser.parse("  ");
    }

    @Test
    public void parse_validArg_success() throws Exception {
        // valid file path
        String validFilePath = "ValidFile.xml";
        assertEquals(parser.parse(validFilePath), new ImportCommand(validFilePath));

        // valid file path with leading and trailing whitespaces
        String validFilePathWithSpaces = "  ValidFile.xml   ";
        assertEquals(parser.parse(validFilePathWithSpaces), new ImportCommand(validFilePath));

    }
}
