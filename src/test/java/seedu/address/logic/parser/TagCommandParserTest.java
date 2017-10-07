package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class TagCommandParserTest {
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        TagCommand expectedTagCommand =
                new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, "friends colleagues", expectedTagCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }
}
