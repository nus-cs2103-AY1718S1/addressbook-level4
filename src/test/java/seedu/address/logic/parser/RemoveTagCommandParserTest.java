package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_indexAndTagSpecified_success() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        // have tags
        final String tagName = "friends";
        Tag toRemove = new Tag(tagName);
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + tagName;
        RemoveTagCommand expectedCommand = new RemoveTagCommand(indexes, toRemove);
        assertParseSuccess(parser, userInput, expectedCommand);

        // specified tag doesn't exist
        final String nonExistentTagName = "hello";
        Tag nonExistentTag = new Tag(nonExistentTagName);
        userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + nonExistentTagName;
        expectedCommand = new RemoveTagCommand(indexes, nonExistentTag);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_tagSpecified_failure() throws Exception {
        // no indexes
        ArrayList<Index> indexes = new ArrayList<Index>();
        final String tagName = "friends";
        Tag toRemove = new Tag(tagName);
        String userInput = " " + PREFIX_TAG.toString() + tagName;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagName = "friends";
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;

        // no tags
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString();
        assertParseFailure(parser, userInput, MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemoveTagCommand.COMMAND_WORDVAR_1, expectedMessage);
    }
}
