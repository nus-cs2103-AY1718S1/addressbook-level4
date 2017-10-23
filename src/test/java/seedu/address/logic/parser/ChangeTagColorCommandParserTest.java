package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;


public class ChangeTagColorCommandParserTest {
    private ChangeTagColorCommandParser parser = new ChangeTagColorCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final TagColor tagColor = new TagColor("red");
        final Set<Tag> tags = new HashSet<>();
        Tag tag1 = new Tag("family");
        tags.add(tag1);

        // one tag
        String userInput = PREFIX_TAG.toString() + tag1.tagName + " " + PREFIX_COLOR.toString() + tagColor.tagColorName;
        ChangeTagColorCommand expectedCommand = new ChangeTagColorCommand(tags, tagColor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // more than one tag
        Tag tag2 = new Tag("friend");
        tags.add(tag2);
        userInput += " " + PREFIX_TAG.toString() + tag2.tagName;
        expectedCommand = new ChangeTagColorCommand(tags, tagColor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, ChangeTagColorCommand.COMMAND_WORD, expectedMessage);
    }
}
