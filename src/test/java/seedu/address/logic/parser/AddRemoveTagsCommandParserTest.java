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

import seedu.address.logic.commands.AddRemoveTagsCommand;
import seedu.address.model.tag.Tag;

//@@author kenpaxtonlim
public class AddRemoveTagsCommandParserTest {
    private AddRemoveTagsCommandParser parser = new AddRemoveTagsCommandParser();

    @Test
    public void parse_argsSpecified_success() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);

        String userInputAdd = " add " + INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandAdd = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputAdd, expectedCommandAdd);

        String userInputRemove = " remove " + INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandRemove = new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputRemove, expectedCommandRemove);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AddRemoveTagsCommand.COMMAND_WORD, expectedMessage);
    }
}
