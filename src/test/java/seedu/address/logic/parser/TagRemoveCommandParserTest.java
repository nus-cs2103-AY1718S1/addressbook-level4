package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.TagRemoveCommand;

public class TagRemoveCommandParserTest {

    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE);

    private TagRemoveCommandParser parser = new TagRemoveCommandParser();

    @Test
    public void parseMissingPartsFailure() {

        // no user input
        assertParseFailure(parser, TAG_EMPTY, MESSAGE_INVALID_FORMAT);
    }

    /*
    @Test
    public void parseTagRemoveSuccess() throws Exception{
        Index targetIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(targetIndex);

        Set<Tag> tagSet = new HashSet<Tag>();
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagAddDescriptor addDescriptor= new TagAddDescriptor();
        addDescriptor.setTags(tagSet);
        TagAddCommand tagAdd = new TagAddCommand(singlePersonIndexList, addDescriptor);
        tagAdd.executeUndoableCommand();

        String userInput = VALID_TAG_FRIEND + SPACE + targetIndex.getOneBased();
        TagRemoveCommand.TagRemoveDescriptor descriptor = new TagRemoveDescriptor();
        descriptor.setTags(tagSet);
        TagRemoveCommand expectedCommand = new TagRemoveCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    */
}
