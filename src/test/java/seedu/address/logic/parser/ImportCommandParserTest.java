//@@author danielbrzn
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String service = "google";
        assertParseSuccess(parser, service, new ImportCommand(service));
    }

    @Test
    public void parse_missingField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE);

        // missing the service to import from
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_tooManyFields_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE);

        // too many arguments
        assertParseFailure(parser, "google icloud", expectedMessage);
    }
}
