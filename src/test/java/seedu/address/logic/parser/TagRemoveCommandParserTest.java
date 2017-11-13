package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
import seedu.address.model.tag.Tag;

//@@author ZhangH795

public class TagRemoveCommandParserTest {

    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE);

    private TagRemoveCommandParser parser = new TagRemoveCommandParser();

    @Test
    public void parseMissingPartsFailure() {

        // no user input
        assertParseFailure(parser, TAG_EMPTY, MESSAGE_INVALID_FORMAT);
        // no tagName provided
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseTagRemoveSuccess() throws Exception {
        Index targetIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(targetIndex);

        Set<Tag> tagSet = new HashSet<Tag>();
        tagSet.add(new Tag(VALID_TAG_FRIEND));

        String userInput = targetIndex.getOneBased() + " " + VALID_TAG_FRIEND;
        TagRemoveCommand.TagRemoveDescriptor descriptor = new TagRemoveDescriptor();
        descriptor.setTags(tagSet);
        TagRemoveCommand expectedCommand = new TagRemoveCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
