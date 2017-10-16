package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.AddTagsCommand;
import seedu.address.model.tag.Tag;

public class AddTagsCommandParserTest {
    private AddTagsCommandParser parser = new AddTagsCommandParser();

    @Test
    public void parse_indexSpecifiedtagsSpecified_success() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);

        String userInput = " " + INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND;
        AddTagsCommand expectedCommand = new AddTagsCommand(INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AddTagsCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_indexSpecifiednoTagsSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, Integer.toString(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);
    }

    @Test
    public void parse_noIndexSpecifiedTagsSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TAG_HUSBAND, expectedMessage);
    }
}
