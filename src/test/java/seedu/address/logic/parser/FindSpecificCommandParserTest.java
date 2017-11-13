package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindSpecificCommand;
import seedu.address.model.person.EmailContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.TagContainsSpecifiedKeywordsPredicate;


//@@author aver0214
public class FindSpecificCommandParserTest {

    private FindSpecificCommandParser parser = new FindSpecificCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPrefixWithEmptyArgs_throwsParseException() {
        // name prefix with empty args
        assertParseFailure(parser, "n/    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));

        // phone number prefix with empty args
        assertParseFailure(parser, "p/    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));

        // email prefix with empty args
        assertParseFailure(parser, "e/    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));

        // tag prefix with empty args
        assertParseFailure(parser, "t/    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindSpecificCommand() {
        // Parse names with no leading and trailing whitespaces
        FindSpecificCommand expectedFindSpecificCommand =
            new FindSpecificCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindSpecificCommand);

        // Parse names with multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Alice  Bob    ", expectedFindSpecificCommand);

        // Parse phone numbers with no leading and trailing whitespaces
        expectedFindSpecificCommand =
            new FindSpecificCommand(
                new PhoneContainsSpecifiedKeywordsPredicate(Arrays.asList("85355255", "12334321")));

        // Parse phone numbers with multiple white spaces between keywords
        assertParseSuccess(parser, "   p/    85355255    12334321", expectedFindSpecificCommand);

        // Parse  emails with no leading and trailing whitespaces
        expectedFindSpecificCommand =
            new FindSpecificCommand(new EmailContainsSpecifiedKeywordsPredicate(
                Arrays.asList("alice@example.com", "bob@example.com")));

        // Parse emails with multiple white spaces between keywords
        assertParseSuccess(parser, "   e/    alice@example.com    bob@example.com",
            expectedFindSpecificCommand);

        // Parse  tags with no leading and trailing whitespaces
        expectedFindSpecificCommand =
            new FindSpecificCommand(new TagContainsSpecifiedKeywordsPredicate(
                Arrays.asList("[friends]", "[important]")));

        // Parse tags with multiple white spaces between keywords
        assertParseSuccess(parser, "   t/    [friends]    [important]", expectedFindSpecificCommand);
    }
} //@@author
