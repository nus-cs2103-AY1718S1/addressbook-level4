//@@author aggarwalRuchir
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {

    private static final String INVALID_TAG_1 = "invalid";
    private static final String INVALID_TAG_2 = "wrong";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "criminal";
    private static final String VALID_TAG_3 = "teammate";
    private static final String VALID_TAG_4 = "family";

    private static final String TAG_DESC_CRIMINAL = " " + PREFIX_TAG + VALID_TAG_2;
    private static final String TAG_DESC_FAMILY = " " + PREFIX_TAG + VALID_TAG_4;
    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_1;
    private static final String TAG_DESC_INVALID = " " + PREFIX_TAG + INVALID_TAG_1;
    private static final String TAG_DESC_NOARGUMENT = " " + PREFIX_TAG;
    private static final String TAG_DESC_TEAMMATE = " " + PREFIX_TAG + VALID_TAG_3;
    private static final String TAG_DESC_WRONG = " " + PREFIX_TAG + INVALID_TAG_2;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        assertParseSuccess(parser, ListCommand.COMMAND_WORD, new ListCommand());
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + "  ", new ListCommand());
    }

    @Test
    public void parse_validArgs_returnsListCommand() throws Exception {
        // Valid tags as arguments
        Set<Tag> singleTagSetOne = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> singleTagSetTwo = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_2)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        // List command with one argument
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSetOne)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND, expectedListCommand);

        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSetTwo)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_CRIMINAL, expectedListCommand);


        // List command with multiple arguments
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND + TAG_DESC_CRIMINAL,
                expectedListCommand);

        multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_3),
                new Tag(VALID_TAG_4)));
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND + TAG_DESC_TEAMMATE
                + TAG_DESC_FAMILY, expectedListCommand);

        multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2),
                new Tag(VALID_TAG_3), new Tag(VALID_TAG_4)));
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FAMILY + TAG_DESC_CRIMINAL
                + TAG_DESC_FRIEND + TAG_DESC_TEAMMATE, expectedListCommand);

    }

    @Test
    public void parse_invalidArgs_returnsListCommand() throws Exception {
        Set<Tag> invalidTagSet = new HashSet<Tag>(Arrays.asList(new Tag(INVALID_TAG_1)));
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(invalidTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_INVALID, expectedListCommand);

        invalidTagSet = new HashSet<Tag>(Arrays.asList(new Tag(INVALID_TAG_2)));
        expectedListCommand = new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(invalidTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_WRONG, expectedListCommand);
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, ListCommand.COMMAND_WORD + TAG_DESC_NOARGUMENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
