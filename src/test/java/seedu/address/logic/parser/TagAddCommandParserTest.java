package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.TagAddCommand;

public class TagAddCommandParserTest {

    private static final String TAGADD_COMMAND = "t-add";
    private static final String SPACE = " ";
    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE);

    private TagAddCommandParser parser = new TagAddCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TAGADD_COMMAND + SPACE + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, TAGADD_COMMAND + SPACE + TAG_EMPTY, MESSAGE_INVALID_FORMAT);

        // no user input
        assertParseFailure(parser, TAGADD_COMMAND, MESSAGE_INVALID_FORMAT);
    }
/*
    @Test
    public void parse_tagAdd_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(targetIndex);
        String userInput = TAGADD_COMMAND + SPACE + VALID_TAG_FRIEND + SPACE + targetIndex.getOneBased();
        TagAddDescriptor descriptor = new TagAddDescriptor(new PersonBuilder().
                withTags(VALID_TAG_FRIEND).build());
        TagAddCommand expectedCommand = new TagAddCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}
