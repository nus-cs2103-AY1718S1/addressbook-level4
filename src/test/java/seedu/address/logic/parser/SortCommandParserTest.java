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
    public void parseFailure() {
        //More than 1 argument inputted
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                        + SortCommand.BY_ASCENDING + WHITESPACE + CliSyntax.PREFIX_PHONE + SortCommand.BY_ASCENDING,
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Gibberish arguments inputted, with one valid argument
        assertParseFailure(parser, WHITESPACE + "gibberish/g" + CliSyntax.PREFIX_PHONE + SortCommand.BY_ASCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Sort order not recognised
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_PHONE + "gibberish",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }


    @Test
    public void parseSuccess() {

        assertParseSuccess(parser, "", new SortCommand("n/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.BY_ASCENDING, new SortCommand("n/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE
                + SortCommand.BY_ASCENDING, new SortCommand("p/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL
                + SortCommand.BY_ASCENDING, new SortCommand("e/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS
                + SortCommand.BY_ASCENDING, new SortCommand("a/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.BY_DESCENDING, new SortCommand("n/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE
                + SortCommand.BY_DESCENDING, new SortCommand("p/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL
                + SortCommand.BY_DESCENDING, new SortCommand("e/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS
                + SortCommand.BY_DESCENDING, new SortCommand("a/", true));


    }
}
