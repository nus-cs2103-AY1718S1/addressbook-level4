package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnaliasCommand;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AliasCommand}.
 */
public class UnaliasCommandParserTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_validAlias_returnsUnaliasCommand() {
        assertParseSuccess(parser, LIST_COMMAND_ALIAS, new UnaliasCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "two arguments",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }
}
