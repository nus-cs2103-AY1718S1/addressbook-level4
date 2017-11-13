package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.OrderCommand;

//@@author tingtx
public class OrderCommandParserTest {

    private OrderCommandParser parser = new OrderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsOrderCommand() {

        OrderCommand expectedOrderCommand;

        //one parameter
        expectedOrderCommand = new OrderCommand("NAME");

        assertParseSuccess(parser, "NAME", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "nAme", expectedOrderCommand); //case insensitive

        expectedOrderCommand = new OrderCommand("BIRTHDAY");

        assertParseSuccess(parser, "BIRTHDAY", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "birtHDaY", expectedOrderCommand); //case insensitive

        expectedOrderCommand = new OrderCommand("ADDRESS");

        assertParseSuccess(parser, "ADDRESS", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "addresS", expectedOrderCommand); //case insensitive

        expectedOrderCommand = new OrderCommand("TAG");

        assertParseSuccess(parser, "TAG", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "tAg", expectedOrderCommand); //case insensitive


        //two parameters
        expectedOrderCommand = new OrderCommand("TAG NAME");

        assertParseSuccess(parser, "TAG NAME", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "tAG namE", expectedOrderCommand); //case insenstive

        expectedOrderCommand = new OrderCommand("GROUP NAME");

        assertParseSuccess(parser, "GROUP NAME", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "grouP nAmE", expectedOrderCommand); //case insenstive

        expectedOrderCommand = new OrderCommand("TAG NAME");

        assertParseSuccess(parser, "TAG NAME", expectedOrderCommand); //same parameter

        assertParseSuccess(parser, "tAG nAmE", expectedOrderCommand); //case insenstive

    }
}
