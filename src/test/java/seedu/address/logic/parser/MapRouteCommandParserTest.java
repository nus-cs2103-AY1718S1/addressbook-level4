package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.MapRouteCommand;

//@@author dalessr
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class MapRouteCommandParserTest {

    private MapRouteCommandParser parser = new MapRouteCommandParser();
    private String startLocation = "Clementi Street";

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1 a/Clementi Street", new MapRouteCommand(INDEX_FIRST_PERSON, startLocation));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a/    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
    }
}
