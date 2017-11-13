//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT
            + DeleteGroupCommand.MESSAGE_USAGE;
    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();


    @Test
    public void parse_correctGrpNameFormat_success() {
        assertParseSuccess(parser, "testGroupName", new DeleteGroupCommand("testGroupName", false));
        assertParseSuccess(parser, "     testGroupName      ", new DeleteGroupCommand("testGroupName", false));
        assertParseSuccess(parser, "1GrpNameWithNumber", new DeleteGroupCommand("1GrpNameWithNumber", false));
    }

    @Test
    public void parse_correctIndexFormat_success() {
        assertParseSuccess(parser, "   1  ", new DeleteGroupCommand(Index.fromOneBased(1), true));
    }

    @Test
    public void parse_multipleArgument_failure() {
        testFailure("blahh blahhh");
        testFailure("blahh 1 2");
        testFailure("  1 2 3 4");
    }

    @Test
    public void parse_invalidGrpNameFormat_failure() {
        testFailure("-1");
        testFailure("0");
    }


    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSE_FAILURE);
    }
}
//@@author
