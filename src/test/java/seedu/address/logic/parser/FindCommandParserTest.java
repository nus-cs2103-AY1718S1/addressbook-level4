package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author awarenessxz
    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<String> nameList = (List<String>) Arrays.asList("Alice", "Bob");
        List<String> emptyNameList = (List<String>) Arrays.asList(new String [0]);
        List<String> tagList = (List<String>) Arrays.asList("friends", "Colleagues");
        List<String> emptyTagList = (List<String>) Arrays.asList(new String [0]);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(nameList, emptyTagList), 0);
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);

        // Find by Name only
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(nameList, emptyTagList), 0);
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // Find by Tag only
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(emptyNameList, tagList), 0);
        assertParseSuccess(parser, " t/friends Colleagues", expectedFindCommand);

        // Find by both Name and Tag
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(nameList, tagList), 0);
        assertParseSuccess(parser, " n/Alice Bob t/friends Colleagues", expectedFindCommand);

        // Find by tag with sort by email
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(emptyNameList, tagList), 2);
        assertParseSuccess(parser, " t/friends Colleagues s/tag", expectedFindCommand);

    }

}
