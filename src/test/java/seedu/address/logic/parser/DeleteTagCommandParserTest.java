package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no tag specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);

        // multiple tags
        assertParseFailure(parser, "1" + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetIndex, targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllLowerCase_success() {
        String userInput = "all " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllUpperCase_success() {
        String userInput = "ALL " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllMixedCase_success() {
        String userInput = "aLl " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
