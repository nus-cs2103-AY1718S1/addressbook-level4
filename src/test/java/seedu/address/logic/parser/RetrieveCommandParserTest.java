//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.RetrieveCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

public class RetrieveCommandParserTest {

    private RetrieveCommandParser parser = new RetrieveCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        final String expectedMessage = String.format(RetrieveCommand.MESSAGE_EMPTY_ARGS, RetrieveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "*&%nonAlphanumericCharacters!!!%&*", MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsRetrieveCommand() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));

        // no leading and trailing whitespaces
        RetrieveCommand expectedCommand =
                new RetrieveCommand(predicate);
        assertParseSuccess(parser, "friends", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n friends \t \n", expectedCommand);
    }

}
