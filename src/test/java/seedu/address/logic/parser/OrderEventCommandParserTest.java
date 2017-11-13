package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.OrderEventCommand;

//@@author kaiyu92
public class OrderEventCommandParserTest {

    private OrderEventCommandParser parser = new OrderEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        OrderEventCommand expectedOrderCommand = new OrderEventCommand("TITLE");

        //same value
        assertParseSuccess(parser, "TITLE", expectedOrderCommand);

        //case insensitive
        assertParseSuccess(parser, "tItLe", expectedOrderCommand);
    }
}
