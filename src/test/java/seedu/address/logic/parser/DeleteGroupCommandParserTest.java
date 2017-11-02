//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT
            + DeleteGroupCommand.MESSAGE_USAGE;
    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();


    @Test
    public void parse_correctGrpNameFormat_success() {
        assertParseSuccess(parser, "testGroupName", new DeleteGroupCommand("testGroupName"));
        assertParseSuccess(parser, "     testGroupName      ", new DeleteGroupCommand("testGroupName"));
        assertParseSuccess(parser, "1GrpNameWithNumber", new DeleteGroupCommand("1GrpNameWithNumber"));
    }

    @Test
    public void parse_multipleArgument_failure() {
        assertParseFailure(parser, "blahh blahhh", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "blahh 1 2", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_invalidGrpNameFormat_failure() {
        assertParseFailure(parser, "1", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "54321", MESSAGE_PARSE_FAILURE);
    }
}
//@@author
