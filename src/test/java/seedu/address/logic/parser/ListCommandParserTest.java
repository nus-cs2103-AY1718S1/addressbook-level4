package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.*;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "criminal";
    private static final String INVALID_TAG_1 = "invalid";

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand();
        assertParseSuccess(parser, " ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() throws Exception{
        // no leading and trailing whitespaces
        Set<Tag> TagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(TagSet)));
        //assertParseSuccess(parser, " t/friends", expectedListCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/friend t/criminal", expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_returnsListCommand() throws Exception {
        Set<Tag> invalidTagSet = new HashSet<Tag>(Arrays.asList(new Tag(INVALID_TAG_1)));
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(invalidTagSet)));
        assertParseSuccess(parser, " t/invalidTag", expectedListCommand);
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
