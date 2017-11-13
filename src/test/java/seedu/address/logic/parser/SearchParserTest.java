package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.person.NamePhoneTagContainsKeywordsPredicate;

//@@author willxujun
public class SearchParserTest {

    private SearchParser parser = new SearchParser();

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new NamePhoneTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchCommand);
    }

    @Test
    public void parse_whitespaceArgs_returnsListCommand() {
        ListCommand expectedListCommand =
                new ListCommand();
        assertParseSuccess(parser, "    ", expectedListCommand);
    }

}
