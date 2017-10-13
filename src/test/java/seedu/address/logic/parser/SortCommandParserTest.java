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
    public void parse_sortOrderNotSpecified_failure() {
        assertParseFailure(parser, SortCommand.COMMAND_WORD + WHITESPACE + CliSyntax.PREFIX_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_notSingleAttribute_failure() {

        //No argument inputted
        assertParseFailure(parser, SortCommand.COMMAND_WORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //No recognised argument inputted
        assertParseFailure(parser, SortCommand.COMMAND_WORD + "z/" + SortCommand.BY_ASCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //More than 1 argument inputted
        assertParseFailure(parser, SortCommand.COMMAND_WORD + WHITESPACE + CliSyntax.PREFIX_NAME
                        + SortCommand.BY_ASCENDING + WHITESPACE + CliSyntax.PREFIX_PHONE + SortCommand.BY_ASCENDING,
                String.format(SortCommand.MESSAGE_MULTIPLE_ATTRIBUTE, SortCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, SortCommand.COMMAND_WORD + WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.BY_ASCENDING, new SortCommand("n/", false));

    }

}
