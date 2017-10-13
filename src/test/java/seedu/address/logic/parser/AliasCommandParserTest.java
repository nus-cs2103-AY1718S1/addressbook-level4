package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ListCommand;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AliasCommand}.
 */
public class AliasCommandParserTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_noArgs_returnsAliasListCommand() {
        assertParseSuccess(parser, "", new AliasCommand());
    }

    @Test
    public void parse_validArgs_returnsAliasCommand() {
        assertParseSuccess(parser, LIST_COMMAND_ALIAS + " " + ListCommand.COMMAND_WORD,
                new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }
}
