package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.commands.FindByTagsCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Containts unit tests for the FindCommand
 */
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    //@@author marvinchin
    @Test
    public void parse_moreThanOneOption_throwsParseException() {
        String input = "-tag -fav";
        assertParseFailure(parser, input, FindCommandParser.INVALID_FIND_COMMAND_FORMAT_MESSAGE);
    }

    @Test
    public void parse_invalidOption_throwsParseException() {
        String input = "-someinvalidoption123";
        assertParseFailure(parser, input, FindCommandParser.INVALID_FIND_COMMAND_FORMAT_MESSAGE);
    }

    @Test
    public void parse_tagOptionNoArgs_throwsParseException() {
        String input = "-tag     ";
        assertParseFailure(parser, input, FindCommandParser.INVALID_FIND_COMMAND_FORMAT_MESSAGE);
    }

    @Test
    public void parse_tagOptionValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindByTagsCommand(new TagsContainKeywordsPredicate(Arrays.asList("colleagues", "friends")));
        assertParseSuccess(parser, "-tag colleagues friends", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "-tag   \n colleagues \t friends \n", expectedFindCommand);
    }
    //@@author

    @Test
    public void parse_emptyArg_throwsParseException() {
        String input = "    ";
        assertParseFailure(parser, input, FindCommandParser.INVALID_FIND_COMMAND_FORMAT_MESSAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
