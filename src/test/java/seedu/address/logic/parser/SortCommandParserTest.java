package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidArgs_failure() {

        //More than 1 field specified
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                         + WHITESPACE + CliSyntax.PREFIX_ADDRESS + SortCommand.REVERSE_ORDER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Invalid field specified
        assertParseFailure(parser, WHITESPACE + "invalid/i",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Invalid sort order specified
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_NAME + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_optionalSortOrderArg_success() {
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME.toString(), new SortCommand("n/", false));
    }

    @Test
    public void parse_validArgs_success() {

        // No arguments supplied
        assertParseSuccess(parser, "", new SortCommand("n/", false));

        // Valid args to sort by name in ascending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME.toString(),
                new SortCommand("n/", false));

        // Valid args to sort by name in descending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.REVERSE_ORDER, new SortCommand("n/", true));

        // Valid args to sort by phone in ascending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE,
                new SortCommand("p/", false));

        // Valid args to sort by phone in descending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE
                + SortCommand.REVERSE_ORDER, new SortCommand("p/", true));

        // Valid args to sort by email in ascending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL,
                new SortCommand("e/", false));

        // Valid args to sort by email in descending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL
                + SortCommand.REVERSE_ORDER, new SortCommand("e/", true));

        // Valid args to sort by address in ascending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS,
                new SortCommand("a/", false));

        // Valid args to sort by address in descending order
        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS
                + SortCommand.REVERSE_ORDER, new SortCommand("a/", true));

    }
}

