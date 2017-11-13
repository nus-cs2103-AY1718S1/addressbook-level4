package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_ASCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DEFAULT_ORDER;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DESCENDING_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

//@@author k-l-a
public class SortCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidPrefix_failure() {
        //not a prefix
        assertParseFailure(parser, "notAPrefix", MESSAGE_INVALID_FORMAT);

        //valid order but bad prefix
        assertParseFailure(parser, "notAPrefix " + ARGUMENT_ASCENDING_WORD, MESSAGE_INVALID_FORMAT);

        //no space between prefix and order
        assertParseFailure(parser, "n/asc", MESSAGE_INVALID_FORMAT);

        //prefix and order are in the wrong order
        assertParseFailure(parser, "asc n/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidOrder_failure() {
        //not an order
        assertParseFailure(parser, "n/ notOrder", MESSAGE_INVALID_FORMAT);

        //two valid prefixes
        assertParseFailure(parser, PREFIX_NAME.getPrefix()
                + " " + PREFIX_PHONE.getPrefix(), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_failure() {
        //more than 2 arguments
        assertParseFailure(parser, PREFIX_PHONE.getPrefix() + " " + ARGUMENT_DESCENDING_WORD
                + " some other uselessarguments", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        //no arguments
        assertParseSuccess(parser, "", new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD));

        //just order
        assertParseSuccess(parser, ARGUMENT_DESCENDING_WORD,
                new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD));

        //just prefix
        assertParseSuccess(parser, PREFIX_EMAIL.getPrefix(),
                new SortCommand(PREFIX_EMAIL.getPrefix(), ARGUMENT_ASCENDING_WORD));

        //valid prefix and order
        assertParseSuccess(parser, PREFIX_NAME.getPrefix() + " " + ARGUMENT_ASCENDING_WORD,
                new SortCommand(PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD));
    }
}
