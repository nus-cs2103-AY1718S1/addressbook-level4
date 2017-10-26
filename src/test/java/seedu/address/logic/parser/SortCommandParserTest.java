package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();


    @Test
    public void parse_invalidArgs_failure() {

        assertParseFailure(parser, " n/ p/", SortCommand.SORT_MULTIPLE_INPUT);

        assertParseFailure(parser, "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "", new SortCommand(PREFIX_NAME.toString()));

        assertParseSuccess(parser, " n/", new SortCommand(PREFIX_NAME.toString()));

        assertParseSuccess(parser, " p/", new SortCommand(PREFIX_PHONE.toString()));

        assertParseSuccess(parser, " e/", new SortCommand(PREFIX_EMAIL.toString()));

        assertParseSuccess(parser, " a/", new SortCommand(PREFIX_ADDRESS.toString()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

}
