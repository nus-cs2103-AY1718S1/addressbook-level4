package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author CindyTsai1
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ArrayList<String>(Arrays.asList("n/", "li a")));
        assertParseSuccess(parser, " n/li a", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n li a  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //birthday month is out of bound
        assertParseFailure(parser, " b/15", String.format(FindCommand.MESSAGE_BIRTHDAYKEYWORD_NONEXIST, "15"));

        //birthday month is a single digit
        assertParseFailure(parser, " b/1", String.format(FindCommand.MESSAGE_BIRTHDAYKEYWORD_INVALID, "1"));

        //birthday month is not integer
        assertParseFailure(parser, " b/abc", FindCommand.MESSAGE_BIRTHDAYKEYWORD_NONNUMBER);
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() {
        assertParseFailure(parser, " tt/abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
