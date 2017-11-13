package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

//@@author nadhira15
public class DeleteTagCommandParserTest {
    public static final String VALID_TAG = "neighbours";
    public static final String VALID_TAG_2 = "friends";
    public static final String INVALID_TAG = "hubby*";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_missingFieldSpecified_failure() throws Exception {
        // no index and tag specified
        assertParseFailure(parser, null , MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_TAG , MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1 " , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_wrongFieldTypeSpecified_failure() throws Exception {
        // both tags
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "-3 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 " + VALID_TAG, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_TAG + " " + VALID_TAG, MESSAGE_INVALID_FORMAT);

        // invalid tag
        assertParseFailure(parser, "1 " + INVALID_TAG, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_wrongOrderSpecified_failure() throws Exception {
        // specifying tag before index
        assertParseFailure(parser, VALID_TAG + " 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgument_success() throws Exception {
        Index index = INDEX_FIRST_PERSON;
        Set<Tag> tagToDelete = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand expectedCommand = new DeleteTagCommand(index, tagToDelete);
        assertParseSuccess(parser, "1 " + VALID_TAG, expectedCommand);

        Set<Tag> tagsToDelete = ParserUtil.parseTags(Arrays.asList(VALID_TAG, VALID_TAG_2));
        DeleteTagCommand secondExpectedCommand = new DeleteTagCommand(index, tagsToDelete);
        assertParseSuccess(parser, "1 " + VALID_TAG + " " + VALID_TAG_2, secondExpectedCommand);
    }
}
