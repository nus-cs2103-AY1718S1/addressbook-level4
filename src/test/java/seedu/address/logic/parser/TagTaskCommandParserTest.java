package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tasks.TagTaskCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class TagTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagTaskCommand.MESSAGE_USAGE);

    private TagTaskCommandParser parser = new TagTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_URGENT, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws Exception {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_NOT_URGENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_URGENT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreambleMixedWithValidPreamble_success() throws Exception {
        String userInput = "-5, 0, 1" + TAG_DESC_FRIEND;
        TagTaskCommand expectedCommand = new TagTaskCommand(new Index[]{INDEX_FIRST_TASK},
                SampleDataUtil.getTagSet(VALID_TAG_FRIEND));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // empty tag prefix
        assertParseFailure(parser, "1, 2" + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid tag followed by valid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + TAG_DESC_NOT_URGENT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // valid tag followed by invalid tag
        assertParseFailure(parser, "1" + TAG_DESC_NOT_URGENT + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_URGENT + TAG_DESC_NOT_URGENT + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_URGENT + TAG_EMPTY + TAG_DESC_NOT_URGENT,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_URGENT + TAG_DESC_NOT_URGENT,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws Exception {
        // multiple indices and multiple tags
        Index[] targetIndices = new Index[]{INDEX_SECOND_TASK, INDEX_THIRD_TASK};
        Set<Tag> newTags = SampleDataUtil.getTagSet(VALID_TAG_URGENT, VALID_TAG_GROUP);
        String userInput = targetIndices[0].getOneBased() + ", " + targetIndices[1].getOneBased() + TAG_DESC_URGENT
                + TAG_DESC_GROUP;

        TagTaskCommand expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);

        // single index and multiple tags
        targetIndices = new Index[]{INDEX_FIRST_TASK};
        newTags = SampleDataUtil.getTagSet(VALID_TAG_URGENT, VALID_TAG_GROUP);
        userInput = targetIndices[0].getOneBased() + TAG_DESC_URGENT + TAG_DESC_GROUP;

        expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);

        // multiple indices and single tag
        targetIndices = new Index[]{INDEX_SECOND_TASK, INDEX_THIRD_TASK};
        newTags = SampleDataUtil.getTagSet(VALID_TAG_NOT_URGENT);
        userInput = targetIndices[0].getOneBased() + ", " + targetIndices[1].getOneBased() + TAG_DESC_NOT_URGENT;

        expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);

        // single index and single tag
        targetIndices = new Index[]{INDEX_FIRST_TASK};
        newTags = SampleDataUtil.getTagSet(VALID_TAG_NOT_URGENT);
        userInput = targetIndices[0].getOneBased() + TAG_DESC_NOT_URGENT;

        expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_success() throws Exception {
        // repeated indices
        Index[] targetIndices = new Index[]{INDEX_SECOND_TASK, INDEX_SECOND_TASK};
        Set<Tag> newTags = SampleDataUtil.getTagSet(VALID_TAG_URGENT, VALID_TAG_GROUP);
        String userInput = targetIndices[0].getOneBased() + ", " + targetIndices[1].getOneBased() + TAG_DESC_URGENT
                + TAG_DESC_GROUP;

        TagTaskCommand expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);

        // repeated tags
        targetIndices = new Index[]{INDEX_SECOND_TASK, INDEX_THIRD_TASK};
        newTags = SampleDataUtil.getTagSet(VALID_TAG_URGENT);
        userInput = targetIndices[0].getOneBased() + ", " + targetIndices[1].getOneBased() + TAG_DESC_URGENT
                + TAG_DESC_URGENT + TAG_DESC_URGENT;

        expectedCommand = new TagTaskCommand(targetIndices, newTags);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
