//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewGroupCommand;

public class ViewGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT + ViewGroupCommand.MESSAGE_USAGE;

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parse_groupName_success() {
        assertParseSuccess(parser, "testGroupName", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "     testGroupName      ", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "        1GrpNameWithNumber  ", new ViewGroupCommand("1GrpNameWithNumber"));
    }

    @Test
    public void parse_index_success() {
        Index testIdx = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new ViewGroupCommand(testIdx));
        assertParseSuccess(parser, "  1      ", new ViewGroupCommand(testIdx));
        testIdx = Index.fromOneBased(999);
        assertParseSuccess(parser, "   999    ", new ViewGroupCommand(testIdx));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " -10  ", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, " 0  ", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_missingArgument_failure() {
        assertParseFailure(parser, "", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_multipleArguments_failure() {
        assertParseFailure(parser, "hi lol", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "hi 1 blah blah", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "1 lol blah blahh", MESSAGE_PARSE_FAILURE);
    }
}
//@@author
