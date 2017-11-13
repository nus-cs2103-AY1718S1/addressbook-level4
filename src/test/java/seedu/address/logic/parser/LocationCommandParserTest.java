package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.LocationCommand;

//@@author sidhmads
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the LocationCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the LocationCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class LocationCommandParserTest {

    private LocationCommandParser parser = new LocationCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        List<String> valid = new ArrayList<>();
        valid.add("1");
        assertParseSuccess(parser, "1", new LocationCommand(valid));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
    }
}
