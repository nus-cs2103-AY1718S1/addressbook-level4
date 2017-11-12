package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;

public class FindCommandParserTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidSortArgsEmptyDataArgsThrowsParseException() {
        assertParseFailure(parser, " " + SORT_ARGUMENT_NAME_DESCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(
                        new PersonDataContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        new ArrayList<>());
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parseArgumentsNullStringThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        FindCommandParser.parseArguments(null);
    }

    @Test
    public void parseArgumentsEmptyStringReturnsNull() {
        assertEquals(null, FindCommandParser.parseArguments(""));
        assertEquals(null, FindCommandParser.parseArguments("         "));
    }

    @Test
    public void parseArgumentsValidArgsReturnsSpaceAppendedArgs() {
        assertEquals(" some args", FindCommandParser.parseArguments("some args"));
        assertEquals(" some args", FindCommandParser.parseArguments("    some args"));
    }

}
