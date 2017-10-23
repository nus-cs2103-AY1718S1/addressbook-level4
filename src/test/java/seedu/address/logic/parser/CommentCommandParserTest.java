package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.model.person.Comment;

public class CommentCommandParserTest {
    private CommentCommandParser parser = new CommentCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Comment comment = new Comment("Some comment.");

        // have comments
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_COMMENT.toString() + " " + comment;
        CommentCommand expectedCommand = new CommentCommand(INDEX_FIRST_PERSON, comment);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no comments
        userInput = targetIndex.getOneBased() + " " + PREFIX_COMMENT.toString();
        expectedCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, CommentCommand.COMMAND_WORD, expectedMessage);
    }
}
