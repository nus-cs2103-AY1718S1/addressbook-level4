//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_EMPTY_INDEX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.UntagCommand;
import seedu.address.model.tag.Tag;

public class UntagCommandParserTest {

    private static final String VALID_TAG_NAMES = "friends/enemies";

    private static final String VALID_INDEX_LIST = "1,2,3";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NO_INDEXES =
            String.format(MESSAGE_EMPTY_INDEX_LIST, UntagCommand.MESSAGE_USAGE);

    private UntagCommandParser parser = new UntagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no indexes specified
        assertParseFailure(parser, VALID_TAG_NAMES, MESSAGE_INVALID_FORMAT);

        // no indexes and no tag name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no indexes
        assertParseFailure(parser, ",,,, " + VALID_TAG_NAMES, MESSAGE_NO_INDEXES);

        // negative index
        assertParseFailure(parser, "-5,-1" + VALID_TAG_NAMES, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0, 0,  0 " + VALID_TAG_NAMES, MESSAGE_INVALID_FORMAT);

        // indexes are not all integers
        assertParseFailure(parser, "1,2,three " + VALID_TAG_NAMES, MESSAGE_INVALID_FORMAT);

        // invalid tag name
        assertParseFailure(parser, VALID_INDEX_LIST + " friends/!@#$", MESSAGE_TAG_CONSTRAINTS);

        // invalid arguments being parsed
        assertParseFailure(parser, "1,2,three dummy friends/enemies", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsUntagCommand() throws Exception {
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("enemies");

        // no leading and trailing whitespaces
        UntagCommand expectedCommand = new UntagCommand(false, Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), Arrays.asList(secondTag, firstTag));
        assertParseSuccess(parser, VALID_INDEX_LIST + " " + VALID_TAG_NAMES, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t " + VALID_INDEX_LIST + " \n"
                + VALID_TAG_NAMES + "\t \n", expectedCommand);

        // multiple duplicated indexes
        assertParseSuccess(parser, "1,1,1,2,2,3" + " " + VALID_TAG_NAMES, expectedCommand);

        // remove all tags from the specified persons
        expectedCommand = new UntagCommand(false, Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), Collections.emptyList());
        assertParseSuccess(parser, "  1,2,3  ", expectedCommand);

        // remove all tags
        expectedCommand = new UntagCommand(true, Collections.emptyList(), Collections.emptyList());
        assertParseSuccess(parser, "  -all  ", expectedCommand);

        // remove a tag from all persons
        expectedCommand = new UntagCommand(true, Collections.emptyList(),
                Arrays.asList(secondTag, firstTag));
        assertParseSuccess(parser, " -all  " + " friends/enemies  ", expectedCommand);
    }

}
