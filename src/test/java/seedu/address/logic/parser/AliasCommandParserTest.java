//@@author danielbrzn
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AliasCommand;

public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String alias = "a";
        final String command = "add";
        assertParseSuccess(parser, alias + " "
                + command, new AliasCommand(alias, command));
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AliasCommand.MESSAGE_USAGE);

        // missing the command to map alias to
        assertParseFailure(parser, "b", expectedMessage);

        // missing everything
        assertParseFailure(parser, AliasCommand.COMMAND_WORD, expectedMessage);

    }
}
