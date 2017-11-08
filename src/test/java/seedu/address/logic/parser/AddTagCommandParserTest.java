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
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_indexAndTagSpecified_success() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        // have tags
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + tagName;
        AddTagCommand expectedCommand = new AddTagCommand(indexes, toAdd);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_tagSpecified_failure() throws Exception {
        // no indexes
        ArrayList<Index> indexes = new ArrayList<Index>();
        final String tagName = "friends";
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
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AddTagCommand.COMMAND_WORDVAR_1, expectedMessage);
    }
}
