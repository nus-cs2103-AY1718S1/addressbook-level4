//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_EMPTY_INDEX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {

    private static final String VALID_TAG_NAME = "friends";

    private static final String VALID_INDEX_LIST = "1,2,3";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NO_INDEXES =
            String.format(MESSAGE_EMPTY_INDEX_LIST, TagCommand.MESSAGE_USAGE);

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no indexes specified
        assertParseFailure(parser, VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // no tag name specified
        assertParseFailure(parser, VALID_INDEX_LIST, MESSAGE_INVALID_FORMAT);

        // no indexes and no tag name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no indexes
        assertParseFailure(parser, ",,,, " + VALID_TAG_NAME, MESSAGE_NO_INDEXES);

        // negative index
        assertParseFailure(parser, "-5 " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // indexes are not all integers
        assertParseFailure(parser, "1,2,three " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // invalid tag name
        assertParseFailure(parser, VALID_INDEX_LIST + " !@#$", MESSAGE_TAG_CONSTRAINTS);

        // invalid arguments being parsed
        assertParseFailure(parser, "1,2,three dummy tag", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsTagCommand() throws Exception {
        // no leading and trailing whitespaces
        TagCommand expectedCommand = new TagCommand(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), new Tag(VALID_TAG_NAME));
        assertParseSuccess(parser, VALID_INDEX_LIST + " " + VALID_TAG_NAME, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t " + VALID_INDEX_LIST + " \n"
                + VALID_TAG_NAME + "\t \n", expectedCommand);

        // multiple duplicated indexes
        expectedCommand = new TagCommand(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), new Tag(VALID_TAG_NAME));
        assertParseSuccess(parser, "1,1,1,2,2,3" + " " + VALID_TAG_NAME, expectedCommand);
    }

}
