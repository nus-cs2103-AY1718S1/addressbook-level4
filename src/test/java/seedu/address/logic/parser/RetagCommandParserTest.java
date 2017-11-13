//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RetagCommand.MESSAGE_INVALID_ARGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.RetagCommand;
import seedu.address.model.tag.Tag;

public class RetagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetagCommand.MESSAGE_USAGE);

    private static final String MESSAGE_DUPLICATED_TAG_NAMES =
            String.format(MESSAGE_INVALID_ARGS, RetagCommand.MESSAGE_USAGE);

    private RetagCommandParser parser = new RetagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no tag name specified
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FORMAT);

        // only one tag name specified
        assertParseFailure(parser, " friends  ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // invalid tag name
        assertParseFailure(parser, "friends !@#$!", MESSAGE_TAG_CONSTRAINTS);

        // target tag name is the same as new tag name
        assertParseFailure(parser, "friends friends", MESSAGE_DUPLICATED_TAG_NAMES);
    }

    @Test
    public void parse_validArgs_returnsRetagCommand() throws Exception {
        Tag newTag = new Tag("friends");
        Tag targetTag = new Tag("enemies");

        // no leading and trailing whitespaces
        RetagCommand expectedCommand = new RetagCommand(targetTag, newTag);
        assertParseSuccess(parser, "enemies friends", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t enemies \n friends \t \n", expectedCommand);
    }
}
