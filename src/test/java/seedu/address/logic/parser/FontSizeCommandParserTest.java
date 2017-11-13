package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FontSizeCommand.ACCEPTED_PARAMETERS;
import static seedu.address.logic.commands.FontSizeCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FontSizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//author charlesgoh
public class FontSizeCommandParserTest {
    private FontSizeCommandParser parser = new FontSizeCommandParser();

    @Test
    public void parse_wrongArguments_failure() {
        // No arguments specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Wrong arguments specified
        assertParseFailure(parser, "random input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArguments_success() throws ParseException {
        FontSizeCommand expectedCommand;
        FontSizeCommand actualCommand;

        for (String arg: ACCEPTED_PARAMETERS) {
            expectedCommand = new FontSizeCommand(arg);

            // Check for command word
            actualCommand = parser.parse(arg);
            assertEquals(actualCommand.getOption(), expectedCommand.getOption());

            // Check for command alias
            actualCommand = parser.parse(arg);
            assertEquals(actualCommand.getOption(), expectedCommand.getOption());
        }
    }
}
