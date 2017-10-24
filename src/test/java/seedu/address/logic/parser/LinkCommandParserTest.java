package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.model.person.Link;

public class LinkCommandParserTest {
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Link link = new Link("facebook.com");

        // have link
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LINK.toString() + " " + link;
        LinkCommand expectedCommand = new LinkCommand(INDEX_FIRST_PERSON, link);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no link
        userInput = targetIndex.getOneBased() + " " + PREFIX_LINK.toString();
        expectedCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, LinkCommand.COMMAND_WORD, expectedMessage);
    }
}
